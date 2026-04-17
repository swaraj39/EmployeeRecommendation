package com.example.demo.LoginService;


import com.example.demo.Models.ApiPermissions;
import com.example.demo.Repository.APIPermissionsRepo;
import com.example.demo.Services.PermissionServiceRedis;
import com.example.demo.Services.UserSerivesRedis;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceCheck {

    private final APIPermissionsRepo apiPermissionsRepo;
    private final PermissionServiceRedis permissionServiceRedis;

    public LoginServiceCheck(APIPermissionsRepo apiPermissionsRepo, PermissionServiceRedis permissionServiceRedis) {
        this.apiPermissionsRepo = apiPermissionsRepo;
        this.permissionServiceRedis = permissionServiceRedis;
    }


    public boolean isAllowed(String method, String role, String uri) {

        List<ApiPermissions> permissions = permissionServiceRedis.getPermissionsByRole(role);
        System.out.println(permissions);

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
