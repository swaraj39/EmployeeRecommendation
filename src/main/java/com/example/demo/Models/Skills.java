package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    private String name;

//    @ManyToMany(mappedBy = "skills")
//    @JsonIgnore
//    private List<Employees> employees;
//
//    @ManyToMany(mappedBy = "projectSkills")
//    @JsonIgnore
//    private List<Project> projects;
}
