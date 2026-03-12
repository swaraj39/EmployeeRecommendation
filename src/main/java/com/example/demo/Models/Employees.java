package com.example.demo.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private Long phone;
    @NotNull
    private Long experience;
    @NotNull
    private boolean avaibility;
    @NotNull
    private String seniority;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeSkill> employeeSkills;

    @OneToMany(mappedBy = "empid", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProjectRecommendation> projectRecommendations;






}
