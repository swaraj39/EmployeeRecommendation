package com.example.demo.Repository;

import com.example.demo.Models.ApiPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface APIPermissionsRepo extends JpaRepository<ApiPermissions, Long> {
    List<ApiPermissions> findByMethodname(String methodname);

    List<ApiPermissions> findByRolename(String role);
}
