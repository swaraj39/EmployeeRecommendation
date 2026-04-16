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

    public boolean isAllowed(String method, String role, String uri) {

        List<ApiPermissions> permissions = apiPermissionsRepo.findByRolename(role);


        for (ApiPermissions p : permissions) {

            boolean methodMatch =
                    p.getMethodname().equalsIgnoreCase(method);
            boolean endpointMatch =
                    uri.toLowerCase().contains(p.getEndpoint().toLowerCase());
            if (methodMatch && endpointMatch) {
                return true;
            }
        }

        return false;
    }
}
