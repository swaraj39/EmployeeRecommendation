package com.example.demo.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeRegisterRequest {
    // User fields
    private String username;
    private String password;
    private String role;

    // Employee fields
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private Long experience;
    private boolean availability;
    private String seniority;

    // ✅ Skills
    private List<SkillProficiency> skills;
}
