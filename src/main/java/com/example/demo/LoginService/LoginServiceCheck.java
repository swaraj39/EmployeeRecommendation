package com.example.demo.LoginService;


import com.example.demo.Models.ApiPermissions;
import com.example.demo.Repository.APIPermissionsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceCheck {

    private final APIPermissionsRepo apiPermissionsRepo;
    public LoginServiceCheck(APIPermissionsRepo apiPermissionsRepo) {
        this.apiPermissionsRepo = apiPermissionsRepo;
    }
    public boolean isAllowed(String method, String role){
        List<ApiPermissions> byMethodname = apiPermissionsRepo.findByMethodname(method);
        return byMethodname.stream()
                .anyMatch(role1 -> role1.getRolename().equals(role));
    }
}
