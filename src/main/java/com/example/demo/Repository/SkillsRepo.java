package com.example.demo.Repository;

import com.example.demo.Models.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillsRepo extends JpaRepository<Skills, Long> {
    Optional<Skills> findByName(String name);
}
