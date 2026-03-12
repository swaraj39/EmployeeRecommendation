package com.example.demo.Controllers;

import com.example.demo.DTOs.EMPReommendDTO;
import com.example.demo.DTOs.EmployeeRequest;
import com.example.demo.DTOs.ProjectRequest;
import com.example.demo.Models.Skills;
import com.example.demo.Services.FrontService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
@Tag(name = "Employee Project Recommendation", description = "APIs for managing employees, projects, skills and recommendations")
public class FrontController {

    private final FrontService frontService;

    @Operation(
            summary = "Add Employee",
            description = "Creates a new employee and stores their skills with proficiency levels"
    )
    @PostMapping("/add/emp")
    public ResponseEntity<?> addEmpSkills(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(frontService.addEmpSkills(request));
    }

    @Operation(
            summary = "Add Project",
            description = "Creates a new project and defines required skills and proficiency levels"
    )
    @PostMapping("/add/project")
    public ResponseEntity<?> addProjectSkills(@RequestBody ProjectRequest request) {
        return ResponseEntity.ok(frontService.addProjectSkills(request));
    }

    @Operation(
            summary = "Generate Recommendation",
            description = "Evaluates employees and generates recommendations for the given project"
    )
    @PostMapping("/add/requirement/{id}")
    public ResponseEntity<?> addRecommendation(@PathVariable Long id) {
        frontService.generateRecommendation(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Add Skill",
            description = "Adds a new skill to the system if it does not already exist"
    )
    @PostMapping("/add/skill")
    public ResponseEntity<?> addSkill(@RequestBody Skills request) {
        return ResponseEntity.ok(frontService.addSkill(request));
    }

    @Operation(
            summary = "Get Project Recommendations",
            description = "Returns ranked employees recommended for a project"
    )
    @GetMapping("/get/recommendations/{id}")
    public ResponseEntity<List<EMPReommendDTO>> getRecommendations(@PathVariable Long id) {
        return ResponseEntity.ok(frontService.getRecommendations(id));
    }
}