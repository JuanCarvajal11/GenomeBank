package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private List<String> roles;
    private String username;

    public static AuthResponse fromUserData(String username, String token, List<String> roles) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .roles(roles != null ? roles : List.of())
                .username(username)
                .build();
    }
}