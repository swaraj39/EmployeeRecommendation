package com.example.demo.Repository;

import com.example.demo.Models.Project;
import com.example.demo.Models.ProjectRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRecommendationRepo extends JpaRepository<ProjectRecommendation, Long> {
    List<ProjectRecommendation> findAlByProject(Project project);

    void deleteAllByProjectId(Long projectId);
}
