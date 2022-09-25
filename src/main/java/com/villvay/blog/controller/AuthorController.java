package com.villvay.blog.controller;

import com.villvay.blog.dto.request.AuthorRequest;
import com.villvay.blog.dto.request.CommentRequest;
import com.villvay.blog.dto.response.AuthorResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/authors")
public class AuthorController {

    @Autowired
    @Qualifier(value = "AuthorService")
    private IResourceService<AuthorResponse, AuthorRequest, PostResponse> authorService;

    @GetMapping
    public ResponseEntity<RequestResponse<AuthorResponse>> getAllAuthors() {
        RequestResponse<AuthorResponse> response = authorService.getAllResources();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<AuthorResponse>> getAuthorsById(@PathVariable("id") Long authorId) {
        RequestResponse<AuthorResponse> response = authorService.getResourceById(authorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<RequestResponse<PostResponse>> getPostsByAuthorId(@PathVariable("id") Long authorId) {
        RequestResponse<PostResponse> response = authorService.getRelatedResourcesByResourceId(authorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RequestResponse<AuthorResponse>> addAuthor(@RequestBody AuthorRequest authorRequest) {
        RequestResponse<AuthorResponse> response = authorService.addResource(authorRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<AuthorResponse>> editAuthor(@RequestBody AuthorRequest authorRequest, @PathVariable("id") Long authorId){
        RequestResponse<AuthorResponse> response = authorService.updateResource(authorRequest, authorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<AuthorResponse>> deleteAuthor(@PathVariable("id") Long authorId){
        RequestResponse<AuthorResponse> response = authorService.deleteByResourceId(authorId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
