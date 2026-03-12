package com.example.demo.DTOs;


import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
        private String projectName;
        private String projectDescription;
        private Long experience;
        private boolean avaibility;
        private String seniority;

        private List<ProjectSkillProficiency> skills; // Reuse SkillProficiency DTO
    }

