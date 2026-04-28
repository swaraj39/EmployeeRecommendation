package com.example.demo.Service;

import com.example.demo.Config.JwtUtil;
import com.example.demo.Controllers.SecurityController;
import com.example.demo.DTOs.EmployeeRegisterRequest;
import com.example.demo.DTOs.SkillProficiency;
import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Skills;
import com.example.demo.Models.Users;
import com.example.demo.Repository.EmployeeSkillRepo;
import com.example.demo.Repository.SkillsRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Services.PermissionServiceRedis;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

    @Mock
    private PermissionServiceRedis permissionServiceRedis;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private SkillsRepo skillRepo;

    @Mock
    private EmployeeSkillRepo employeeSkillRepo;

    @InjectMocks
    private SecurityController securityController;

    @Test
    void testRegisterUser() {

        // Arrange
        EmployeeRegisterRequest request = new EmployeeRegisterRequest();
        request.setUsername("john_doe");
        request.setPassword("password123");
        request.setRole("USER");
        request.setName("John");
        request.setSurname("Doe");
        request.setEmail("john@test.com");
        request.setPhone(9876543210L);
        request.setExperience(5L);
        request.setAvailability(true);
        request.setSeniority("MID");

        SkillProficiency skill = new SkillProficiency();
        skill.setSkillId(1L);
        skill.setProficiency("Expert");
        request.setSkills(List.of(skill));

        Skills mockSkill = Skills.builder()
                .id(1L)
                .name("Java")
                .build();

        when(passwordEncoder.encode("password123")).thenReturn("encoded_pass");
        when(skillRepo.findById(1L)).thenReturn(Optional.of(mockSkill));
        when(userRepo.save(any(Users.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ResponseEntity<?> response = securityController.register(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Users savedUser = (Users) response.getBody();
        assertNotNull(savedUser);
        assertEquals("john_doe", savedUser.getUsername());
        assertEquals("encoded_pass", savedUser.getPassword());

        // Verify interactions
        verify(userRepo).save(any(Users.class));
        verify(employeeSkillRepo).save(any(EmployeeSkill.class));
        verify(skillRepo).findById(1L);
    }
}