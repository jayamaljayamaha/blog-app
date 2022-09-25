package com.villvay.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthRequest {
    @Schema(name = "userName", example = "user", required = true)
    private String userName;
    @Schema(name = "password", example = "password", required = true)
    private String password;
}
