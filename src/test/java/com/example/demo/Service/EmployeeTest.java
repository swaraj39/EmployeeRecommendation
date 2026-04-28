package com.example.demo.Service;

import com.example.demo.DTOs.EmployeeRegisterRequest;
import com.example.demo.DTOs.EmployeeRequest;
import com.example.demo.DTOs.SkillProficiency;
import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;
import com.example.demo.Models.Skills;
import com.example.demo.Repository.EmployeeRepo;
import com.example.demo.Repository.EmployeeSkillRepo;
import com.example.demo.Repository.SkillsRepo;
import com.example.demo.Services.EmployeeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeeTest {
    /**
     * todo 1. Mock - It will create the dummy object for the actual process
     * todo 2. ExtendWith - helps with to extend with the current task for the execution
     * todo 3. InjectMocks - Injects the object in that class
     */

    @Mock
    EmployeeRepo employeeRepo;
    @Mock
    EmployeeSkillRepo employeeSkillRepo;
    @InjectMocks
    EmployeeService employeeService;



    @Test
    void getEmployee() {
        // Arrange (mock behavior)
        when(employeeRepo.findAll()).thenReturn(List.of(new Employees()));

        // Act
        var result = employeeService.getAllEmployees();

        // Print
        log.info("getEmployee {}", result);
        System.out.println("Employees from the test case is : " + result);
    }

    @Test
    void getEmployeeId() {
        Employees emp = new Employees();
        emp.setName("John");  // ✅ important!
        emp.setId(1L);

        when(employeeRepo.findById(1L)).thenReturn(Optional.of(emp));

        var result = employeeService.getEmployeeById(1L);

        log.info("getEmployeeId {}", result);
        assertNotNull(result);
    }

    @SneakyThrows
    @Test
    void TestPrivateMethods() {
        Method getEmployee = EmployeeService.class.getDeclaredMethod("getEmployee", String.class);
        getEmployee.setAccessible(true);

        // ❌ Use invalid input
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> {
            getEmployee.invoke(employeeService, "");
        });

        assertTrue(ex.getCause() instanceof RuntimeException);
        assertEquals("Name cannot be empty", ex.getCause().getMessage());

        // ✅ valid case
        boolean test = (boolean) getEmployee.invoke(employeeService, "test");
        assertTrue(test);
    }


}
