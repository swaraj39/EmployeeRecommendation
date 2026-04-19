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

        // 🔥 normalize URI (same fix as filter)
        if (uri.startsWith("/api")) {
            uri = uri.substring(4);
        }

        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }

        uri = uri.toLowerCase();

        List<ApiPermissions> permissions = permissionServiceRedis.getPermissionsByRole(role);
        System.out.println(permissions);

        for (ApiPermissions p : permissions) {

            boolean methodMatch =
                    p.getMethodname().equalsIgnoreCase(method);

            boolean endpointMatch =
                    uri.startsWith(p.getEndpoint().toLowerCase());  // ✅ FIXED

            if (methodMatch && endpointMatch) {
                return true;
            }
        }

        return false;
    }
}
