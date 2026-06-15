package com.example.clientservicedemo.service;

import com.example.clientservicedemo.clients.AuthServiceClient;
import com.example.clientservicedemo.dtos.*;
import com.example.clientservicedemo.entity.ClientUser;
import com.example.clientservicedemo.exceptions.CustomException;
import com.example.clientservicedemo.util.MessageConstant;
import com.example.clientservicedemo.util.Utility;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.clientservicedemo.repo.ClientUserRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    ClientUserRepo clinetUserRepo;

    @Autowired
    FileOps fileOps;

    @Autowired
    Utility utility;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper mapper;

    @Autowired
    AuthServiceClient authServiceClient;


    public ResponseData saveClinet(ClientDTO clientDTO) throws IOException {

        
        if(clinetUserRepo.existsByEmail(clientDTO.getEmail())){

            throw new CustomException(
                    MessageConstant.USER_ALREADY_EXISTS_WITH_EMAIL,
                    HttpStatus.BAD_REQUEST,
                    "USER_ALREADY_EXISTS"
            );
        }
        
        // Generate Client Code
        String clientCode = utility.generateClientCode();

        // Save Files
        String certPath = fileOps.saveCertificate(clientDTO.getCertificate());
        String logoPath = fileOps.saveLogo(clientDTO.getLogo());

        // Create Entity
        ClientUser clientUser = new ClientUser();

        clientUser.setClientCode(clientCode);
        clientUser.setCompanyName(clientDTO.getCompanyName());
        clientUser.setEmail(clientDTO.getEmail());
        clientUser.setMobile(clientDTO.getMobile());
        clientUser.setPassword(passwordEncoder.encode(clientDTO.getPassword()));

        clientUser.setDomainName(clientDTO.getDomainName());
        clientUser.setAddress(clientDTO.getAddress());
        clientUser.setCountry(clientDTO.getCountry());
        clientUser.setState(clientDTO.getState());
        clientUser.setPostalCode(clientDTO.getPostalCode());

        clientUser.setCertificatePath(certPath);
        clientUser.setLogoPath(logoPath);

        clientUser.setIsActive(true);


        // Save Data
        ClientUser savedClient = clinetUserRepo.save(clientUser);

        // Return Response
        return new ResponseData(
                "Client Registered Successfully",
                clientCode
        );
    }


    public LoginResponse clientLogin(LoginClientRequest loginClientRequest) {

        try {

            Optional<ClientUser> userOptional =
                    clinetUserRepo.findByEmail(loginClientRequest.getEmail());

            if (userOptional.isEmpty()) {

                throw new CustomException(
                        MessageConstant.USER_NOT_FOUND_WITH_IDENTIFIER,
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS"
                );
            }

            ClientUser user = userOptional.get();

            System.out.println(user.getEmail());
            System.out.println(loginClientRequest.getEmail());

            if (user.getEmail().equals(loginClientRequest.getEmail())
                    && passwordEncoder.matches(
                    loginClientRequest.getPassword(),
                    user.getPassword()
            )
                    && user.getClientCode().equals(loginClientRequest.getCode())) {


                String clientCode = user.getClientCode();
                String companyName = user.getCompanyName();
                System.out.println("Authentication Started");
                ResponseEntity<Map<String, String>> authResponse =
                        authServiceClient.generateClientToken(

                                new TokenRequest(
                                        loginClientRequest.getEmail(),
                                        "ROLE_CLIENT"
                                )
                        );

                System.out.println("Authentication Completed");
                Map<String, String> tokenResponse = authResponse.getBody();

                String accessToken = tokenResponse.get("accessToken");
                String refreshToken = tokenResponse.get("refreshToken");

                return new LoginResponse(
                        accessToken,
                        refreshToken,
                        clientCode,
                        companyName
                );

            } else {

                throw new CustomException(
                        MessageConstant.INVALID_USERNAME_OR_PASSWORD_OR_CLIENTCODE,
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS"
                );
            }

        } catch (FeignException.FeignClientException e) {

            throw new CustomException(
                    MessageConstant.INVALID_USERNAME_OR_PASSWORD,
                    HttpStatus.UNAUTHORIZED,
                    "INVALID_CREDENTIALS"
            );

        } catch (Exception e) {

            throw new CustomException(
                    MessageConstant.INVALID_USERNAME_OR_PASSWORD_OR_CLIENTCODE,
                    HttpStatus.UNAUTHORIZED,
                    "AUTH_ERROR"
            );
        }
    }
}
