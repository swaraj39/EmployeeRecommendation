package com.example.demo.DTOs;
import lombok.Data;

@Data
public class EmployeeLogin {

    // User fields
    private String username;
    private String password;

    // Employee fields
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private Long experience;
    private boolean avaibility;
    private String seniority;
    private String role;  // "USER" or "ADMIN"
}