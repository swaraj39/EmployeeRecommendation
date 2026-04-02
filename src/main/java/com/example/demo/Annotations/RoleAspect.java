package com.example.demo.Annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAspect {

    @Before("@annotation(roleRequired)")
    public void checkRole(JoinPoint joinPoint, RoleRequired roleRequired) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String requiredRole = roleRequired.role();

        System.out.println("Required Role: " + requiredRole);
        System.out.println("User Roles: " + auth.getAuthorities());

        boolean allowed = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(requiredRole));

        if (!allowed) {
            throw new RuntimeException("Access Denied: You don't have required role");
        }
    }
}