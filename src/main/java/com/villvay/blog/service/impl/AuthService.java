package com.villvay.blog.service.impl;

import com.villvay.blog.dto.request.AuthRequest;
import com.villvay.blog.dto.response.AuthResponse;
import com.villvay.blog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String userName = "user";
    private static final String password = "password";

    public AuthResponse authenticateUser(AuthRequest request){
        AuthResponse response = AuthResponse.builder().build();
        if(userName.equals(request.getUserName()) && password.equals(request.getPassword())){
            response.setToken(jwtUtils.generateToken(request.getUserName()));
            response.setAuthenticated(true);
        }
        return response;
    }
}
