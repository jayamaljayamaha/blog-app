package com.villvay.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostRequest {
    @Schema(name = "title", example = "How to face to a technical interview", required = true)
    @Size(min = 5, message = "Title should be minimum length of 5")
    private String title;
    @Schema(name = "body", example = "blah, blah, ,blah, blah, blah, blah", required = true)
    @Size(min = 5, message = "body should be minimum length of 5")
    @NotNull(message = "body must not be null")
    private String body;
    @Schema(name = "authorId", example = "1", required = true)
    @NotNull(message = "authorId must not be null")
    private Long authorId;
}
