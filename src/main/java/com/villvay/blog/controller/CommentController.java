package com.villvay.blog.controller;

import com.villvay.blog.dto.request.CommentRequest;
import com.villvay.blog.dto.request.PostRequest;
import com.villvay.blog.dto.response.AuthorResponse;
import com.villvay.blog.dto.response.CommentResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/comments")
public class CommentController {

    @Autowired
    @Qualifier(value = "CommentService")
    private IResourceService<CommentResponse, CommentRequest, PostResponse> commentService;

    @GetMapping
    public ResponseEntity<RequestResponse<CommentResponse>> getAllComments() {
        RequestResponse<CommentResponse> response = commentService.getAllResources();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<CommentResponse>> getCommentsById(@PathVariable("id") Long commentId) {
        RequestResponse<CommentResponse> response = commentService.getResourceById(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}/posts")
    public ResponseEntity<RequestResponse<PostResponse>> getPostsByCommentId(@PathVariable("id") Long commentId) {
        RequestResponse<PostResponse> response = commentService.getRelatedResourcesByResourceId(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RequestResponse<CommentResponse>> addComment(@RequestBody CommentRequest commentRequest) {
        RequestResponse<CommentResponse> response = commentService.addResource(commentRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<CommentResponse>> editComment(@RequestBody CommentRequest commentRequest, @PathVariable("id") Long commentId){
        RequestResponse<CommentResponse> response = commentService.updateResource(commentRequest, commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RequestResponse<CommentResponse>> deleteComment(@PathVariable("id") Long commentId){
        RequestResponse<CommentResponse> response = commentService.deleteByResourceId(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
