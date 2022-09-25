package com.villvay.blog.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthorResponse implements Serializable {
    private Long id;
    private String name;
    private String userName;
    private String email;
    private String address;
}
