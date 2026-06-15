package com.example.clientservicedemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileOps {

    private final String CERT_FOLDER= "C:/Users/PRUTHIVIRAJ/Documents/CRM-PROJECT/client-demo/uploads/certificates/";

    private final String LOGO_FOLDER= "C:/Users/PRUTHIVIRAJ/Documents/CRM-PROJECT/client-demo/uploads/logos/";


    public String saveCertificate(MultipartFile file) throws IOException {
        String filepath = CERT_FOLDER + file.getOriginalFilename();
        file.transferTo(new java.io.File(filepath));
        return filepath;
    }

    public String saveLogo(MultipartFile file) throws IOException {
        String filepath = LOGO_FOLDER + file.getOriginalFilename();
        file.transferTo(new java.io.File(filepath));
        return filepath;
    }

}
