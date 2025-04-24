package com.shareyoume.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String name;
    private String profileImageUrl;

    public AuthResponse(String token, Long id, String username,
                        String email, String name, String profileImageUrl) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
