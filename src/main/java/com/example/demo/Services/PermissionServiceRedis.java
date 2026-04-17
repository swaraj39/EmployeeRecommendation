package com.example.demo.Services;

import com.example.demo.Models.ApiPermissions;
import com.example.demo.Repository.APIPermissionsRepo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceRedis {

    private final APIPermissionsRepo repo;

    public PermissionServiceRedis(APIPermissionsRepo repo) {
        this.repo = repo;
    }

    @Cacheable(value = "permissions", key = "#role")
    public List<ApiPermissions> getPermissionsByRole(String role) {
        System.out.println("🔥 DB HIT FOR PERMISSIONS");
        return repo.findByRolename(role);
    }
}
