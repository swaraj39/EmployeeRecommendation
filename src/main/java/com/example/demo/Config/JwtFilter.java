package com.example.demo.Config;

import com.example.demo.LoginService.LoginServiceCheck;
import com.example.demo.Repository.UserRepo;
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

    public JwtFilter(JwtUtil jwtUtil, LoginServiceCheck loginServiceCheck, UserRepo userRepo) {
        this.jwtUtil = jwtUtil;
        this.loginServiceCheck = loginServiceCheck;
        this.userRepo = userRepo;
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
                    pathId = Long.parseLong(parts[parts.length - 1]); // last value
                } catch (Exception e) {
                    // ignore if not numeric
                }

                String username = claims.getSubject();
                Long id = userRepo.findByUsername(username).get().getEmployee().getId();
                log.info(id + " " + pathId);
                String role = claims.get("role", String.class);
                if (role.startsWith("ROLE_")) {
                    role = role.substring(5);
                }
                log.info("USERNAME: " + username);
                log.info("ROLE: " + role);
                if (role.equalsIgnoreCase("USER")) {

                    // 🚫 Block project modification
                    if ((method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("POST"))
                            && uri.toLowerCase().contains("project")) {

                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("USER cannot modify project");
                        return;
                    }

                    // 🔥 Allow update ONLY if same user
                    if ((method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("POST"))
                            && uri.toLowerCase().contains("employees")) {

                        if (pathId != null && !pathId.equals(id)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("You can only update your own data");
                            return;
                        }
                    }
                }
                if(!loginServiceCheck.isAllowed(method, role) ){
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied");
                    return;
                }
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + role);
                System.out.println(authority);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(authority)
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                System.out.println("JWT ERROR: " + e.getMessage()); // 🔥 VERY IMPORTANT
            }
        }

        filterChain.doFilter(request, response);
    }
}