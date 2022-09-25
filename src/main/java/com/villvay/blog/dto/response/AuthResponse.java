package com.villvay.blog.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private boolean isAuthenticated;
    private String token;
}
