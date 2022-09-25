package com.villvay.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class PostResponse implements Serializable {
    private Long id;
    private String title;
    private String body;
    private Long authorId;
}
