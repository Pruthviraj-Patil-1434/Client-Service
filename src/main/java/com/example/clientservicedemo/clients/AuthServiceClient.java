package com.example.clientservicedemo.clients;

import com.example.clientservicedemo.dtos.ApiResponse;
import com.example.clientservicedemo.dtos.LoginClientRequest;
import com.example.clientservicedemo.dtos.SignupClientRequest;
import com.example.clientservicedemo.dtos.TokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="STARTUP-AUTHENTICATION-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/auth/generate-client-token")
    public ResponseEntity<Map<String,String>> generateClientToken(@RequestBody TokenRequest tokenRequest);
}
