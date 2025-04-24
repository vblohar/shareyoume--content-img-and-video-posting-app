package com.shareyoume.services;
import com.shareyoume.DTO.PostRequest;
import com.shareyoume.DTO.PostResponse;
import com.shareyoume.exception.ResourceNotFoundException;
import com.shareyoume.models.Post;
import com.shareyoume.models.User;
import com.shareyoume.repos.PostRepository;
import com.shareyoume.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResponse createPost(PostRequest postRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setMediaUrl(postRequest.getMediaUrl());
        post.setMediaType(postRequest.getMediaType());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return PostResponse.fromPost(savedPost);
    }

    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(PostResponse::fromPost);
    }

    public Page<PostResponse> getPostsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return postRepository.findByUser(user, pageable)
                .map(PostResponse::fromPost);
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return PostResponse.fromPost(post);
    }

    public void deletePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
