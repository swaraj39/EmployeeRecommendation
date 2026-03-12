package com.example.demo.Controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.Skills;
import com.example.demo.Repository.SkillsRepo;

@RestController
public class SkillsController {
    private final SkillsRepo skillsRepo;

    public SkillsController(SkillsRepo skillsRepo) {
        this.skillsRepo = skillsRepo;
    }

    @GetMapping("/get/skills")
    public List<Skills> getSkills(){
        return skillsRepo.findAll();
    }

    @GetMapping("/get")
    public String gett(){
        return "hfger";
    }

    
}
