package com.example.demo.Service;

import com.example.demo.Controllers.SkillsController;
import com.example.demo.Models.Skills;
import com.example.demo.Repository.EmployeeRepo;
import com.example.demo.Repository.EmployeeSkillRepo;
import com.example.demo.Repository.SkillsRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class SkillTest {

    @InjectMocks
    SkillsController skillsController;


    @Mock
    SkillsRepo skillsRepo;

    /**
     * before all start once
     * before each every test case
     * after each every test case
     * after all finish
     */
    @BeforeAll
    public static void setup(){
        System.out.println("Initial step ");
    }
    @Test
    void addSkill() {
        // Input
        List<String> input = List.of("Java");

        // Expected entity
        Skills skill = new Skills();
        skill.setName("Java");

        List<Skills> savedSkills = List.of(skill);

        // Mock behavior
        when(skillsRepo.saveAll(any())).thenReturn(savedSkills);

        // Call method
        var response = skillsController.addSkills(input);
        log.info("Response is {}", response);
        // Assertions
//        assertEquals("200 OK", response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Java", response.getBody().get(0).getName());
    }

    @Test
    void deleteSkill() {
        Long skillId = 1L;
        doNothing().when(skillsRepo).deleteById(skillId);
        skillsRepo.deleteById(skillId);
        Mockito.verify(skillsRepo, Mockito.times(1)).deleteById(skillId);

    }


}