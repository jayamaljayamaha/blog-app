package com.villvay.blog.controller;

import com.villvay.blog.dto.request.AuthRequest;
import com.villvay.blog.dto.response.AuthResponse;
import com.villvay.blog.dto.response.AuthorResponse;
import com.villvay.blog.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    private ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticateUser(request);
        return new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
    }
}
