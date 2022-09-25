package com.villvay.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class CommentResponse implements Serializable {
    private Long id;
    private Long postId;
    private String email;
    private String body;
}
