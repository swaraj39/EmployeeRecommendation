package com.example.demo.Config;

import com.example.demo.DTOs.UserCacheDTO;
import com.example.demo.LoginService.LoginServiceCheck;
import com.example.demo.Models.Users;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Services.UserSerivesRedis;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final LoginServiceCheck loginServiceCheck;
    private final UserRepo userRepo;
    private final UserSerivesRedis userSerivesRedis;

    public JwtFilter(JwtUtil jwtUtil, LoginServiceCheck loginServiceCheck, UserRepo userRepo, UserSerivesRedis userSerivesRedis) {
        this.jwtUtil = jwtUtil;
        this.loginServiceCheck = loginServiceCheck;
        this.userRepo = userRepo;
        this.userSerivesRedis = userSerivesRedis;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            try {
                String token = header.substring(7);
                log.info(request.getRequestURI() + " " + request.getRequestURL());
                String uri = request.getRequestURI();
                String method = request.getMethod();

                Claims claims = jwtUtil.getClaims(token);
                Long pathId = null;
                try {
                    String[] parts = uri.split("/");
                    pathId = Long.parseLong(parts[parts.length - 1]);
                } catch (Exception e) {
                    // ignore
                }

                String username = claims.getSubject();
                UserCacheDTO userByUsername = userSerivesRedis.getUserByUsername(username);
                Long id = userByUsername.getEmployeeId();
                log.info(id + " " + pathId);
                String role = claims.get("role", String.class);
                if (role.startsWith("ROLE_")) {
                    role = role.substring(5);
                }
                log.info("USERNAME: " + username);
                log.info("ROLE: " + role);

                // ✅ Step 1: Check permission from DB
                boolean isAllowed = loginServiceCheck.isAllowed(method, role, uri);
                log.info("Permission check result: {}", isAllowed);  // ADD THIS

                if (!isAllowed) {
                    log.warn("Access Denied - Permission check failed");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied");
                    return;
                }

                log.info("Permission check PASSED");  // ADD THIS

                // ✅ Step 2: Ownership check
                if ((method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("POST"))
                        && uri.toLowerCase().contains("employees") && "USER".equals(role)) {

                    if (pathId != null && !pathId.equals(id)) {
                        log.warn("Access Denied - Ownership check failed");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("You can only update your own data");
                        return;
                    }
                }

                log.info("All checks passed, setting authentication");  // ADD THIS

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

                log.info("Authentication set successfully");  // ADD THIS

            } catch (Exception e) {
                log.error("JWT ERROR: " + e.getMessage(), e);  // ADD stack trace
            }
        }

        log.info("Continuing filter chain");  // ADD THIS
        filterChain.doFilter(request, response);
    }
}