package com.example.demo.Services;

import com.example.demo.DTOs.EmployeeRequest;
import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;
import com.example.demo.Models.Skills;
import com.example.demo.Repository.EmployeeRepo;
import com.example.demo.Repository.EmployeeSkillRepo;
import com.example.demo.Repository.SkillsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeesRepo;
    private final EmployeeSkillRepo employeeSkillRepo;
    private final SkillsRepo skillsRepo;

    public String createEmployee(EmployeeRequest request) {

        Employees emp = Employees.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .experience(request.getExperience())
                .avaibility(request.isAvaibility())
                .seniority(request.getSeniority())
                .build();

        employeesRepo.save(emp);

        List<EmployeeSkill> empSkills = request.getSkills().stream().map(s -> {
            Skills skill = skillsRepo.findById(s.getSkillId())
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            return EmployeeSkill.builder()
                    .employee(emp)
                    .skill(skill)
                    .proficiency(s.getProficiency())
                    .build();
        }).toList();

        employeeSkillRepo.saveAll(empSkills);

        return "Employee saved successfully";
    }

    public List<EmployeeSkill> getAllEmployees() {

        List<Employees> employees = employeesRepo.findAll();
        List<EmployeeSkill> byEmployeeSkills = new ArrayList<>();

        for (Employees emp : employees) {
            byEmployeeSkills.addAll(employeeSkillRepo.findByEmployee(emp));
        }

        return byEmployeeSkills;
    }

    public Map<Long, List<EmployeeSkill>> getEmployeeSkillsGrouped() {

        List<EmployeeSkill> employeeSkillList = employeeSkillRepo.findAll();

        return employeeSkillList.stream()
                .collect(Collectors.groupingBy(es -> es.getEmployee().getId()));
    }

    public Map<String, Object> getEmployeeById(Long id) {

        Employees emp = employeesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<EmployeeSkill> skills = employeeSkillRepo.findByEmployeeId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("employee", emp);
        response.put("skills", skills);

        return response;
    }

    public String updateEmployee(Long id, EmployeeRequest request) {

        Employees emp = employeesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setName(request.getName());
        emp.setSurname(request.getSurname());
        emp.setEmail(request.getEmail());
        emp.setPhone(request.getPhone());
        emp.setExperience(request.getExperience());
        emp.setAvaibility(request.isAvaibility());
        emp.setSeniority(request.getSeniority());

        employeesRepo.save(emp);

        List<EmployeeSkill> oldSkills = employeeSkillRepo.findByEmployeeId(id);
        employeeSkillRepo.deleteAll(oldSkills);

        List<EmployeeSkill> empSkills = request.getSkills().stream().map(s -> {

            Skills skill = skillsRepo.findById(s.getSkillId())
                    .orElseThrow(() -> new RuntimeException("Skill not found"));

            return EmployeeSkill.builder()
                    .employee(emp)
                    .skill(skill)
                    .proficiency(s.getProficiency())
                    .build();

        }).toList();

        employeeSkillRepo.saveAll(empSkills);

        return "Employee updated successfully";
    }

    public String deleteEmployee(Long id) {

        Employees emp = employeesRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<EmployeeSkill> skills = employeeSkillRepo.findByEmployeeId(id);
        employeeSkillRepo.deleteAll(skills);

        employeesRepo.delete(emp);

        return "Employee deleted successfully";
    }

    public List<Employees> searchEmployeeByName(String name) {

        return employeesRepo.findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase())
                        || e.getSurname().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}