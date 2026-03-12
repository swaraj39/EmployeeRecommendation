package com.example.demo.Services;

import com.example.demo.Models.*;
import com.example.demo.Repository.*;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {
    private final SkillsRepo skillsRepo;
    private final EmployeeRepo employeeRepo;
    private final ProjectRepo projectRepo;
    private final ProjectRecommendationRepo projectRecommendationRepo;
    private final EmployeeSkillRepo employeeSkillRepo;
    private final ProjectSkillRequRepo projectSkillRequRepo;

    public EvaluationService(SkillsRepo skillsRepo, EmployeeRepo employeeRepo, ProjectRepo projectRepo,
            ProjectRecommendationRepo projectRecommendationRepo, EmployeeSkillRepo employeeSkillRepo,
            ProjectSkillRequRepo projectSkillRequRepo) {
        this.skillsRepo = skillsRepo;
        this.employeeRepo = employeeRepo;
        this.projectRepo = projectRepo;
        this.projectRecommendationRepo = projectRecommendationRepo;
        this.employeeSkillRepo = employeeSkillRepo;
        this.projectSkillRequRepo = projectSkillRequRepo;
    }

    private int getLevel(String proficiency) {
        return switch (proficiency.toLowerCase()) {
            case "beginner" -> 1;
            case "intermediate" -> 2;
            case "expert" -> 3;
            default -> 0;
        };
    }

    @Transactional
    public void evaluate(Long projectId) {

        Project project = projectRepo.findById(projectId).get();

        List<ProjectSkill> projectSkills = project.getProjectSkills();
        double skillWeight = 50.0 / projectSkills.size();
        double proficiencyWeight = 20.0 / projectSkills.size();
        List<Employees> employees = employeeRepo.findAll();

        projectRecommendationRepo.deleteAllByProjectId(projectId);
        System.out.println("deleted");
        for (Employees emp : employees) {

            int score = 0;
            int expScore = 0;
            List<EmployeeSkill> empSkills = emp.getEmployeeSkills();

            // SKILL + PROFICIENCY SCORE (Max 70)
            for (ProjectSkill ps : projectSkills) {

                for (EmployeeSkill es : empSkills) {

                    if (ps.getSkill().getId().equals(es.getSkill().getId())) {

                        score += skillWeight;

                        int required = getLevel(ps.getRequiredProficiency());
                        int employee = getLevel(es.getProficiency());

                        if (employee == required) {
                            score += proficiencyWeight; // perfect match
                        } else if (employee > required) {
                            score += proficiencyWeight * 0.8; // overqualified
                        } else if (required - employee == 1) {
                            score += proficiencyWeight * 0.5; // slightly less skilled
                        } else {
                            score += proficiencyWeight * 0.3; // more less skilled
                        }

                    }
                }
            }
            Long empExp = emp.getExperience();
            Long projExp = project.getExperience();
            if (empExp >= projExp) {
                // Employee meets or exceeds project requirement → full score
                expScore = 20;
            } else  {
                // Employee has less experience → partial score proportional to requirement
                expScore = (int) Math.round((double) empExp / projExp * 20);
            }
            score += expScore;
            // AVAILABILITY SCORE (Max 10)
            if (emp.isAvaibility()) {
                score += 10;
            }

            // Ensure max score = 100
            if (score > 100) {
                score = 100;
            }

            System.out.println(emp.getName());
            System.out.println(score);

            // DTO
            ProjectRecommendation dto = ProjectRecommendation.builder()
                    .project(project)
                    .empid(emp)
                    .score((long) score)
                    .build();

            projectRecommendationRepo.save(dto);
        }
    }
}
// public void evaluate(Long projectId) {
//
// Project project = projectRepo.findById(projectId).get();
//
// List<ProjectSkill> projectSkills = project.getProjectSkills();
// int l = 50/projectSkills.size();
// List<Employees> employees = employeeRepo.findAll();
//
// for (Employees emp : employees) {
//
// int score = 0;
//
// List<EmployeeSkill> empSkills = emp.getEmployeeSkills();
//
// // SKILL + PROFICIENCY SCORE (Max 70)
// for (ProjectSkill ps : projectSkills) {
//
// for (EmployeeSkill es : empSkills) {
//
// if (ps.getSkill().getId().equals(es.getSkill().getId())) {
//
// score += 10; // skill match
//
// int required = getLevel(ps.getRequiredProficiency());
// int employee = getLevel(es.getProficiency());
//
// if (employee >= required) {
// score += 5; // proficiency match
// }
//
// }
// }
// }
//
// // EXPERIENCE SCORE (Max 20)
// if (emp.getExperience() >= project.getExperience()) {
// score += 20;
// }
//
// // AVAILABILITY SCORE (Max 10)
// if (emp.isAvaibility()) {
// score += 10;
// }
//
// // Ensure max score = 100
// if (score > 100) {
// score = 100;
// }
//
// System.out.println(emp.getName());
// System.out.println(score);
//
// // DTO
// ProjectRecommendation dto = ProjectRecommendation.builder()
// .project(project)
// .empid(emp)
// .score((long) score)
// .build();
//
// projectRecommendationRepo.save(dto);
// }
// }
