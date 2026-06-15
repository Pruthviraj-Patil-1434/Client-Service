package com.example.clientservicedemo.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "client_users", schema = "startup_crm")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_code", unique = true, nullable = false, length = 10)
    private String clientCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mobile", nullable = false, length = 20)
    private String mobile;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "certificate_path", length = 500)
    private String certificatePath;

    @Column(name = "logo_path", length = 500)
    private String logoPath;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}