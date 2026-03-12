package com.example.demo.Repository;


import com.example.demo.Models.Project;
import com.example.demo.Models.ProjectSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSkillRequRepo extends JpaRepository<ProjectSkill, Long> {
    List<String> findByProject(Long id);

    void deleteAllByProject(Project project);
}
