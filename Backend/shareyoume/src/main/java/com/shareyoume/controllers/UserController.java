package com.shareyoume.controllers;

import com.shareyoume.DTO.UserProfile;
import com.shareyoume.exception.ResourceNotFoundException;
import com.shareyoume.models.User;
import com.shareyoume.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        UserProfile profile = new UserProfile(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getBio()
        );

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserProfile> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        // The actual user would be extracted from the JWT token in the security context
        // For simplicity, we're just returning a mock response
        UserProfile profile = new UserProfile(
                1L,
                "currentUser",
                "Current User",
                "current@user.com",
                "https://example.com/profile.jpg",
                "This is my bio"
        );
        return ResponseEntity.ok(profile);

    }
}
