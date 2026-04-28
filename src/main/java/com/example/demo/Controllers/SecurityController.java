package com.example.demo.Controllers;

import com.example.demo.Config.JwtUtil;
import com.example.demo.DTOs.EmployeeLogin;
import com.example.demo.DTOs.EmployeeRegisterRequest;
import com.example.demo.DTOs.SkillProficiency;
import com.example.demo.Enums.Role;
import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;
import com.example.demo.Models.Users;
import com.example.demo.Repository.EmployeeSkillRepo;
import com.example.demo.Repository.SkillsRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Services.PermissionServiceRedis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth/")
public class SecurityController {


    private final PermissionServiceRedis  permissionServiceRedis;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;
    private final SkillsRepo  skillRepo;
    private final EmployeeSkillRepo employeeSkillRepo;

    public SecurityController(PermissionServiceRedis permissionServiceRedis, PasswordEncoder passwordEncoder, UserRepo userRepo, JwtUtil jwtUtil, SkillsRepo skillsRepo, EmployeeSkillRepo employeeSkillRepo) {
        this.permissionServiceRedis = permissionServiceRedis;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.skillRepo = skillsRepo;
        this.employeeSkillRepo = employeeSkillRepo;
    }

    @PostMapping("/api/user")
    public ResponseEntity<?> register(@RequestBody EmployeeRegisterRequest dto) {

        // ✅ Create Employee
        Employees emp = Employees.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .experience(dto.getExperience())
                .avaibility(dto.isAvailability())
                .seniority(dto.getSeniority())
                .build();

        // ✅ Create User
        Users user = Users.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.valueOf(dto.getRole().toUpperCase()))
                .employee(emp)
                .build();

        emp.setUser(user);

        // 💾 Save user + employee
        userRepo.save(user);

        // ✅ Save skills
        if (dto.getSkills() != null) {
            for (SkillProficiency sp : dto.getSkills()) {
                EmployeeSkill es = new EmployeeSkill();
                es.setEmployee(emp);
                es.setSkill(skillRepo.findById(sp.getSkillId()).orElseThrow());
                es.setProficiency(sp.getProficiency());
                employeeSkillRepo.save(es);
            }
        }

        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {

        String username = user.get("username");
        String password = user.get("password");

        // 1️⃣ Fetch from DB
        Users admin = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Compare encoded password
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3️⃣ Generate JWT
//        String token = jwtUtil.generateToken(username);

//        return ResponseEntity.ok(Map.of("token", token));
        String s = jwtUtil.generateToken(username, String.valueOf(userRepo.findByUsername(username).get().getRole()));
        permissionServiceRedis.clearPermissionCacheByRole( String.valueOf(userRepo.findByUsername(username).get().getRole()));
        return ResponseEntity.ok(s);
    }

}
