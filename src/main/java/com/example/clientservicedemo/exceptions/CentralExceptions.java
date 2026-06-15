package com.example.clientservicedemo.exceptions;

import com.example.clientservicedemo.dtos.ApiResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class CentralExceptions {

    private static final Logger logger = LoggerFactory.getLogger(CentralExceptions.class);


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
       logger.error("CustomException: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)  // Catch-all (least specific)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        logger.error("Unexpected error: ", ex); // Log the *full* exception (including stack trace)
        ApiResponse<Void> response = ApiResponse.error("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Generic message for client
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
