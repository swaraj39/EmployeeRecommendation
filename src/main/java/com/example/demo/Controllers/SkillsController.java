package com.example.demo.Controllers;

import java.util.List;

import com.example.demo.Services.PermissionServiceRedis;
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
    private final PermissionServiceRedis permissionServiceRedis;


    public SkillsController(SkillsRepo skillsRepo, PermissionServiceRedis permissionServiceRedis) {
        this.skillsRepo = skillsRepo;
        this.permissionServiceRedis = permissionServiceRedis;
    }

    @PostMapping("/api/skills")
public ResponseEntity<List<Skills>> addSkills(@RequestBody List<String> skills) {

    if (skills == null || skills.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    List<Skills> skillEntities = skills.stream()
        .map(name -> {
            Skills s = new Skills();
            s.setName(name);
            return s;
        })
        .toList();

    List<Skills> savedSkills = skillsRepo.saveAll(skillEntities);

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

    @GetMapping("/clear-cache")
    public String clearCache() {
        permissionServiceRedis.clearPermissionCache();
        return "Cache cleared!";
    }

    
}
