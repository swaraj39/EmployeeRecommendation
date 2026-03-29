package com.example.demo.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.Skills;
import com.example.demo.Repository.SkillsRepo;

@RestController
public class SkillsController {
    private final SkillsRepo skillsRepo;

    public SkillsController(SkillsRepo skillsRepo) {
        this.skillsRepo = skillsRepo;
    }

    @PostMapping("/api/skills")
    public ResponseEntity<List<Skills>> addSkills(@RequestBody List<Skills> skills) {
    if (skills == null || skills.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    List<Skills> savedSkills = skillsRepo.saveAll(skills);
    return ResponseEntity.ok(savedSkills);
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
