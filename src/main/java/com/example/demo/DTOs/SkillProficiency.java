package com.example.demo.DTOs;


import lombok.Data;

@Data
public class SkillProficiency {
    private Long skillId;
    private String proficiency; // Beginner / Intermediate / Expert
}
