package com.shareyoume.controllers;

import com.shareyoume.DTO.PostRequest;
import com.shareyoume.DTO.PostResponse;
import com.shareyoume.services.PostService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")

public class PostController {
    private final PostService postService;

    private final String uploadsDir = "src/main/resources/static/uploads/";

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PostResponse> createPost(@ModelAttribute PostRequest postRequest,
                                                   @RequestParam(value = "media", required = false) MultipartFile file) {
        File dir = new File(uploadsDir);
        if(!dir.exists()) dir.mkdir();

        String fileName =(UUID.randomUUID() + " " + file.getOriginalFilename());
        Path filePath = Paths.get(uploadsDir + fileName);

        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//      postRequest.setMediaUrl(PostService.convertImageToBase64((File) file));
        postRequest.setMediaType(file.getContentType());
        postRequest.setMediaName(fileName);
        postRequest.setMediaUrl("http://localhost:8080/uploads/" + fileName);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(postService.createPost(postRequest, username));
    }

    @GetMapping("/media/{mediaName}")
    public ResponseEntity<Resource> getMedia(@PathVariable String mediaName) throws FileNotFoundException {
        Path filePath = Paths.get("D:\\deepseek\\shareyoume- content, img and video posting app\\Backend\\shareyoume\\src\\main\\resources\\static\\uploads\\").resolve(mediaName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        if (!resource.exists()){
            throw new FileNotFoundException("File not found: " + mediaName);
        }

      return ResponseEntity.ok().body(resource);
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
