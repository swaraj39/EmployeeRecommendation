package com.example.demo.DTOs;

import java.io.Serializable;

public class UserCacheDTO implements Serializable {

    private Long employeeId;
    private String role;

    public UserCacheDTO(Long employeeId, String role) {
        this.employeeId = employeeId;
        this.role = role;
    }

    public Long getEmployeeId() { return employeeId; }
    public String getRole() { return role; }
}