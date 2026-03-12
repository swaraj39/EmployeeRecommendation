package com.example.demo.Controllers;

import com.example.demo.DTOs.EmployeeRequest;
import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;
import com.example.demo.Services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "Operations related to Employee management")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee along with associated skills"
    )
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @Operation(
            summary = "Get All Employees",
            description = "Fetch all employees with their skills"
    )
    @GetMapping
    public ResponseEntity<List<EmployeeSkill>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(
            summary = "Get Employee Skills Grouped",
            description = "Returns employee skills grouped by employee ID"
    )
    @GetMapping("/grouped")
    public ResponseEntity<Map<Long, List<EmployeeSkill>>> getEmployeeSkillsGrouped() {
        return ResponseEntity.ok(employeeService.getEmployeeSkillsGrouped());
    }

    @Operation(
            summary = "Get Employee by ID",
            description = "Fetch employee details and their skills"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(
            summary = "Update Employee",
            description = "Updates employee details and skill set"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @Operation(
            summary = "Delete Employee",
            description = "Deletes employee and associated skills"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @Operation(
            summary = "Search Employee",
            description = "Search employee by name or surname"
    )
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Employees>> searchEmployee(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.searchEmployeeByName(name));
    }
}