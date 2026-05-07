package com.example.clientservicedemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Client Service Working";
    }
}