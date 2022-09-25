package com.villvay.blog.exceptions;


import lombok.Getter;

@Getter
public class ResourceNotFoundException extends CustomException{

    private String resource;

    public ResourceNotFoundException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
