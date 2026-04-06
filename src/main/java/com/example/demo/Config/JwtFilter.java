package com.example.demo.Config;

import com.example.demo.LoginService.LoginServiceCheck;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
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
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final LoginServiceCheck loginServiceCheck;

    public JwtFilter(JwtUtil jwtUtil, LoginServiceCheck loginServiceCheck) {
        this.jwtUtil = jwtUtil;
        this.loginServiceCheck = loginServiceCheck;
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
                String method = request.getMethod();
                Claims claims = jwtUtil.getClaims(token);

                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                System.out.println("USERNAME: " + username);
                System.out.println("ROLE: " + role);
                if(!loginServiceCheck.isAllowed(method, role)){
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