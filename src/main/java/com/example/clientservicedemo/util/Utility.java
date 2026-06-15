package com.example.clientservicedemo.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Utility {

    public String generateClientCode() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}


