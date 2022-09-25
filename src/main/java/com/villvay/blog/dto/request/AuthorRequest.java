package com.villvay.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AuthorRequest {
    @Schema(name = "name", example = "jayamal")
    private String name;
    @Schema(name = "userName", example = "jayamaljayamaha", required = true)
    @NotNull(message = "userName must not be null")
    private String userName;
    @Schema(name = "email", example = "jayamaljayamaha2@gmail.com", required = true)
    @NotNull(message = "Email must not be null")
    @Email(message = "Email should be in valid email type")
    private String email;
    @Schema(name = "address", example = "Ja-Ela")
    private String address;
}
