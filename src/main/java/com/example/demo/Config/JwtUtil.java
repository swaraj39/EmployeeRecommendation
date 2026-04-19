package com.example.demo.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "K9fT2mX8qLrV4zYp1cWd7sBn3hGj6QeU0tRa5kLmN2c="; // min 32 chars
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());




    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("my-client-id")
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isExpired(token);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}