package com.shareyoume.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserProfile {
    public UserProfile(Long id, String username, String name, String email, String profileImageUrl, String bio) {
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    private Long id;
    private String username;
    private String name;
    private String email;
    private String profileImageUrl;
    private String bio;
    private int followerCount;
    private int followingCount;
    private int postCount;
    private boolean isFollowing; 
}
