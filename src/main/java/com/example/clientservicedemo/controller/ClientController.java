package com.example.clientservicedemo.controller;

import com.example.clientservicedemo.dtos.*;
import com.example.clientservicedemo.exceptions.CustomException;
import com.example.clientservicedemo.service.ClientService;
import com.example.clientservicedemo.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData> saveClinet(
            @ModelAttribute ClientDTO clientDTO) {

        try {

            ResponseData response =
                    clientService.saveClinet(clientDTO);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        }catch (CustomException e) {

           ResponseData errorResponse = new ResponseData(
                            e.getMessage(),
                                null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);

        } catch (Exception e) {

           ResponseData errorResponse=new ResponseData(
                            "Client Registration Failed",
                                null);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(
            @RequestBody LoginClientRequest loginClientRequest) {

        try {


            LoginResponse response =
                    clientService.clientLogin(loginClientRequest);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);

        } catch (CustomException e) {

            ApiResponse<Map<String, String>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    e.getStatus().value()
            );

            return ResponseEntity.status(e.getStatus()).body(errorResponse);

        } catch (Exception e) {

            ApiResponse<Map<String, String>> errorResponse = ApiResponse.error(
                    MessageConstant.INVALID_USERNAME_OR_PASSWORD_OR_CLIENTCODE + e.getMessage(),
                    HttpStatus.UNAUTHORIZED.value()
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse);
        }
    }

}
