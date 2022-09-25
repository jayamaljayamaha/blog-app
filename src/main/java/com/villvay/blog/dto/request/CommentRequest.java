package com.villvay.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class CommentRequest {
    @Schema(name = "postId", example = "1", required = true)
    @NotNull(message = "postId must not be null")
    @NotBlank(message = "Should have a value to the postId")
    private Long postId;
    @Schema(name = "email", example = "someone@gmail.com", required = true)
    @NotNull(message = "email must not be null")
    @Email(message = "Email should be in valid email type")
    private String email;
    @Schema(name = "body", example = "Nice blog article", required = true)
    @NotNull(message = "body must not be null")
    @Size(min = 5, message = "body should be minimum length of 5")
    private String body;
}
