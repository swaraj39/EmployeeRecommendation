package com.example.demo.DTOs;

import java.util.List;

import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;

import lombok.Data;

@Data
public class DisplayEmployee {
    private Employees employees;
    private List<EmployeeSkill> employeeSkills;
}
