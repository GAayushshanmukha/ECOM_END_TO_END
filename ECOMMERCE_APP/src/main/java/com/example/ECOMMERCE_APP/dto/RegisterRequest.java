package com.example.ECOMMERCE_APP.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    // We omit 'role' so users can't make themselves ADMINS during sign-up
}