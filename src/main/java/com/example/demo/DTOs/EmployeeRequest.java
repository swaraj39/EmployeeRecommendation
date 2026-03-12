package com.example.demo.DTOs;


import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequest  {
    private String name;
    private String surname;
    private String email;
    private Long phone;
    private Long experience;
    private boolean avaibility;
    private String seniority;

    private List<SkillProficiency> skills;
}
