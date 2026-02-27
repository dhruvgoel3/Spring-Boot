package com.example.evebizz.dto;

import com.example.evebizz.enums.Role;
import lombok.Builder;
import lombok.Data;

public class AuthResponses {

    @Data @Builder
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private UserInfo user;
    }

    @Data @Builder
    public static class UserInfo {
        private Long id;
        private String email;
        private String fullName;
        private Role role;
        private boolean isVerified;
        private String avatarUrl;
    }

    @Data @Builder
    public static class MessageResponse {
        private String message;
    }
}