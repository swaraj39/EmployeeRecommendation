package com.example.demo.Models;

import com.example.demo.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    // ✅ real primary key
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(unique = true, nullable = false)
    private String username;  // login username

//    private String name;
//    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Employees employee;
}
