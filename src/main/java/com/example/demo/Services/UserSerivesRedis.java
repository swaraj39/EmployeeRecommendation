package com.example.demo.Services;


import com.example.demo.DTOs.UserCacheDTO;
import com.example.demo.Models.Users;
import com.example.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserSerivesRedis {

        @Autowired
        private UserRepo userRepo;

    @Cacheable(value = "users", key = "#username")
    public UserCacheDTO getUserByUsername(String username) {
        System.out.println("🔥 DB HIT");

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserCacheDTO(
                user.getEmployee().getId(),
                user.getRole().name()
        );
    }
}
