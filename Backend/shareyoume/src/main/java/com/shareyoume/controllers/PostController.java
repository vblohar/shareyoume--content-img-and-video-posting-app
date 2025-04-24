package com.shareyoume.controllers;

import com.shareyoume.DTO.PostRequest;
import com.shareyoume.DTO.PostResponse;
import com.shareyoume.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")

public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PostResponse> createPost( @ModelAttribute PostRequest postRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(postService.createPost(postRequest, username));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @PathVariable String username,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsByUser(username, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        postService.deletePost(id, username);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
