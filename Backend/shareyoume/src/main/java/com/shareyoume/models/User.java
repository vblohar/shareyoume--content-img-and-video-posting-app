package com.shareyoume.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "users", uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
    })
    @Setter
    @Getter
    public class User implements UserDetails {
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        public Set<Post> getPosts() {
            return posts;
        }

        public void setPosts(Set<Post> posts) {
            this.posts = posts;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String username;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String name;

        private String profileImageUrl;
        private String bio;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Post> posts = new HashSet<>();

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

