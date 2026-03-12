package com.example.demo.Services;

import com.example.demo.DTOs.ProjectRequest;
import com.example.demo.DTOs.ProjectSkillProficiency;
import com.example.demo.Models.Project;
import com.example.demo.Models.ProjectSkill;
import com.example.demo.Models.Skills;
import com.example.demo.Repository.ProjectRepo;
import com.example.demo.Repository.ProjectSkillRequRepo;
import com.example.demo.Repository.SkillsRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepository;

    @Autowired
    private SkillsRepo skillsRepository;

    @Autowired
    private ProjectSkillRequRepo projectSkillRequRepo;

    // CREATE PROJECT
    public Project createProject(ProjectRequest request) {

        Project project = Project.builder()
                .projectName(request.getProjectName())
                .projectDescription(request.getProjectDescription())
                .experience(request.getExperience())
                .avaibility(request.isAvaibility())
                .seniority(request.getSeniority())
                .build();

        projectRepository.save(project);

        List<ProjectSkill> requirements = request.getSkills().stream().map(sp -> {

            Skills skill = skillsRepository.findById(sp.getSkillId())
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            return ProjectSkill.builder()
                    .project(project)
                    .skill(skill)
                    .requiredProficiency(sp.getProficiency())
                    .build();

        }).toList();

        projectSkillRequRepo.saveAll(requirements);

        return project;
    }

    // GET ALL PROJECTS
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // GET PROJECT BY ID
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    // UPDATE PROJECT
    @Transactional
    public Project updateProject(Long id, ProjectRequest updatedProject) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProjectName(updatedProject.getProjectName());
        project.setProjectDescription(updatedProject.getProjectDescription());
        project.setExperience(updatedProject.getExperience());
        project.setAvaibility(updatedProject.isAvaibility());
        project.setSeniority(updatedProject.getSeniority());

        projectSkillRequRepo.deleteAllByProject(project);
        project.getProjectSkills().clear();

        List<ProjectSkill> newSkills = new ArrayList<>();

        for (ProjectSkillProficiency ps : updatedProject.getSkills()) {

            Skills skill = skillsRepository.findById(ps.getSkillId())
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            ProjectSkill projectSkill = ProjectSkill.builder()
                    .project(project)
                    .skill(skill)
                    .requiredProficiency(ps.getProficiency())
                    .build();

            newSkills.add(projectSkill);
        }

        projectSkillRequRepo.saveAll(newSkills);

        return projectRepository.save(project);
    }

    // DELETE PROJECT
    public String deleteProject(Long id) {

        projectRepository.deleteById(id);

        return "Project deleted successfully";
    }
}