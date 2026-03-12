package com.example.demo.Controllers;

import com.example.demo.DTOs.ProjectRequest;
import com.example.demo.Models.Project;
import com.example.demo.Services.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Project API", description = "APIs for managing projects and their required skills")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(
            summary = "Create Project",
            description = "Creates a new project and assigns required skills with proficiency levels"
    )
    @PostMapping
    public Project createProject(@RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    @Operation(
            summary = "Get All Projects",
            description = "Returns a list of all available projects"
    )
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @Operation(
            summary = "Get Project By ID",
            description = "Fetch a specific project using its ID"
    )
    @GetMapping("/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @Operation(
            summary = "Update Project",
            description = "Updates project details and replaces required skills"
    )
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    @Operation(
            summary = "Delete Project",
            description = "Deletes a project using its ID"
    )
    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }
}