package com.villvay.blog.controller;

import com.villvay.blog.dto.request.PostRequest;
import com.villvay.blog.dto.response.CommentResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/posts")
public class PostController {

    private static final String AUTH_HEADER = "Auth";

    @Autowired
    @Qualifier(value = "PostService")
    private IResourceService<PostResponse, PostRequest, CommentResponse> postService;

    @GetMapping
    public ResponseEntity<RequestResponse<PostResponse>> getAllPosts() {
        RequestResponse<PostResponse> response = postService.getAllResources();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<PostResponse>> getPostById(@PathVariable("id") Long postId) {
        RequestResponse<PostResponse> response = postService.getResourceById(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/comments")
    public ResponseEntity<RequestResponse<CommentResponse>> getCommentsByPostId(@PathVariable("id") Long postId) {
        RequestResponse<CommentResponse> response = postService.getRelatedResourcesByResourceId(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RequestResponse<PostResponse>> addPost(@Valid @RequestBody PostRequest postRequest) {
        RequestResponse<PostResponse> response = postService.addResource(postRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<PostResponse>> editPost(@RequestBody PostRequest postRequest, @PathVariable("id") Long postId) {
        RequestResponse<PostResponse> response = postService.updateResource(postRequest, postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<PostResponse>> deletePost(@PathVariable("id") Long postId) {
        RequestResponse<PostResponse> response = postService.deleteByResourceId(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
