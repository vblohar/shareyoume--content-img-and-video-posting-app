package com.shareyoume.controllers;

import com.shareyoume.DTO.UserProfile;
import com.shareyoume.exception.ResourceNotFoundException;
import com.shareyoume.models.User;
import com.shareyoume.repos.UserRepository;
import com.shareyoume.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public UserController(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile profile = new UserProfile();
        profile.setId(user.getId());
        profile.setName(user.getName());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setProfileImageUrl(user.getProfileImageUrl());
        profile.setBio(user.getBio());

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserProfile> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        // The actual user would be extracted from the JWT token in the security context
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid authorization header");
        }
        String jwtToken = authorization.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile profile = new UserProfile();
            profile.setId(user.getId());
        profile.setName(user.getName());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getUsername());
        profile.setProfileImageUrl(user.getProfileImageUrl());
        profile.setBio(user.getBio());

        return ResponseEntity.ok(profile);
    }
}
