package com.example.demo.Services;

import com.example.demo.DTOs.EMPReommendDTO;
import com.example.demo.DTOs.EmployeeRequest;
import com.example.demo.DTOs.ProjectRequest;
import com.example.demo.Models.*;
import com.example.demo.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FrontService {

        private final SkillsRepo skillsRepo;
        private final EmployeeRepo employeeRepo;
        private final ProjectRepo projectRepo;
        private final ProjectRecommendationRepo projectRecommendationRepo;
        private final EmployeeSkillRepo employeeSkillRepo;
        private final ProjectSkillRequRepo projectSkillRequRepo;
        private final EvaluationService evaluationService;

        public String addEmpSkills(EmployeeRequest request) {

                Employees employee = Employees.builder()
                                .name(request.getName())
                                .surname(request.getSurname())
                                .email(request.getEmail())
                                .phone(request.getPhone())
                                .experience(request.getExperience())
                                .avaibility(request.isAvaibility())
                                .seniority(request.getSeniority())
                                .build();

                employeeRepo.save(employee);

                List<EmployeeSkill> employeeSkills = request.getSkills().stream().map(sp -> {

                        Skills skill = skillsRepo.findById(sp.getSkillId())
                                        .orElseThrow(() -> new RuntimeException("Skill not found"));

                        return EmployeeSkill.builder()
                                        .employee(employee)
                                        .skill(skill)
                                        .proficiency(sp.getProficiency())
                                        .build();

                }).toList();

                employeeSkillRepo.saveAll(employeeSkills);

                return "Employee saved with skills and proficiency";
        }

        public String addProjectSkills(ProjectRequest request) {

                Project project = Project.builder()
                                .projectName(request.getProjectName())
                                .projectDescription(request.getProjectDescription())
                                .experience(request.getExperience())
                                .avaibility(request.isAvaibility())
                                .seniority(request.getSeniority())
                                .build();

                projectRepo.save(project);

                List<ProjectSkill> requirements = request.getSkills().stream().map(sp -> {

                        Skills skill = skillsRepo.findById(sp.getSkillId())
                                        .orElseThrow(() -> new RuntimeException("Skill not found"));

                        return ProjectSkill.builder()
                                        .project(project)
                                        .skill(skill)
                                        .requiredProficiency(sp.getProficiency())
                                        .build();

                }).toList();

                projectSkillRequRepo.saveAll(requirements);

                return "Project saved with skill requirements";
        }

        public void generateRecommendation(Long id) {
                evaluationService.evaluate(id);
        }

        public String addSkill(Skills request) {

                if (skillsRepo.findByName(request.getName()).isPresent()) {
                        return "Skill already exists";
                }

                Skills skill = Skills.builder()
                                .name(request.getName())
                                .build();

                skillsRepo.save(skill);

                return "Skill added successfully";
        }

        public List<EMPReommendDTO> getRecommendations(Long id) {

                AtomicInteger rank = new AtomicInteger(1);

                Project project = projectRepo.findById(id).get();

                List<ProjectRecommendation> recommendations = projectRecommendationRepo.findAlByProject(project);

                List<ProjectRecommendation> sorted = recommendations.stream()
                                .sorted(
                                                Comparator.comparing(ProjectRecommendation::getScore).reversed()
                                                                .thenComparing(pr -> pr.getEmpid().getExperience(),
                                                                                Comparator.reverseOrder()))
                                .toList();

                return sorted.stream().map(p -> {

                        Employees emp = p.getEmpid();

                        String empSkills = emp.getEmployeeSkills().stream()
                                        .map(s -> s.getSkill().getName() + "(" + s.getProficiency() + ")")
                                        .reduce((a, b) -> a + ", " + b)
                                        .orElse("None");

                        String projSkills = project.getProjectSkills().stream()
                                        .map(s -> s.getSkill().getName() + "(" + s.getRequiredProficiency() + ")")
                                        .reduce((a, b) -> a + ", " + b)
                                        .orElse("None");
                        List<String> empSkillNames = emp.getEmployeeSkills().stream()
                                        .map(e -> e.getSkill().getName()) // only skill names
                                        .toList();

                        List<String> projSkillNames = project.getProjectSkills().stream()
                                        .map(p1 -> p1.getSkill().getName()) // only skill names
                                        .toList();

                        List<String> matchedSkillsList = projSkillNames.stream()
                                        .filter(empSkillNames::contains) // match by skill name only
                                        .toList();

                        String explanation = "Employee Experience: " + emp.getExperience() + " years. "
                                        +
                                        "Employee Skills: [" + empSkills + "]. " +
                                        "Project Required Skills: [" + projSkills + "]. " +
                                        "Matched Skills: [" + matchedSkillsList + "].";

                        return EMPReommendDTO.builder()
                                        .rank((long) rank.getAndIncrement())
                                        .empid(emp.getId())
                                        .empname(emp.getName() + " " + emp.getSurname())
                                        .score(p.getScore())
                                        .explanation(explanation)
                                        .build();

                }).toList();
        }
}