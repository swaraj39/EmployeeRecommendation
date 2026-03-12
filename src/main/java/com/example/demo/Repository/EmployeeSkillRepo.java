package com.example.demo.Repository;

import com.example.demo.Models.EmployeeSkill;
import com.example.demo.Models.Employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmployeeSkillRepo extends JpaRepository<EmployeeSkill, Long> {
    List<EmployeeSkill> findByEmployeeId(Long id);

    Collection<? extends EmployeeSkill> findByEmployee(Employees emp);

}
