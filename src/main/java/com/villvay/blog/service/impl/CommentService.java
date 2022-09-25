package com.villvay.blog.service.impl;

import com.villvay.blog.dto.request.CommentRequest;
import com.villvay.blog.dto.response.CommentResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.entity.Comment;
import com.villvay.blog.entity.Post;
import com.villvay.blog.exceptions.ResourceNotFoundException;
import com.villvay.blog.repository.ICommentRepository;
import com.villvay.blog.repository.IPostRepository;
import com.villvay.blog.service.IResourceService;
import com.villvay.blog.utils.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("CommentService")
public class CommentService implements IResourceService<CommentResponse, CommentRequest, PostResponse> {

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public RequestResponse<CommentResponse> getAllResources() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponse> responses = comments.stream().map(comment -> {
            CommentResponse response = CommentResponse.builder().postId(comment.getPost().getId()).build();
            BeanUtils.copyProperties(comment, response);
            return response;
        }).toList();
        return new RequestResponse<CommentResponse>(true, responses, null);
    }

    @Override
    public RequestResponse<CommentResponse> getResourceById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Comment"));
        CommentResponse response = CommentResponse.builder().postId(comment.getPost().getId()).build();
        BeanUtils.copyProperties(comment, response);
        return new RequestResponse<CommentResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<CommentResponse> addResource(CommentRequest commentRequest) {
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Post"));
        Comment comment = Comment.builder().post(post).build();
        BeanUtils.copyProperties(commentRequest, comment);
        Comment savedComment = commentRepository.save(comment);
        CommentResponse response = CommentResponse.builder().postId(post.getId()).build();
        BeanUtils.copyProperties(savedComment, response);
        return new RequestResponse<CommentResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<CommentResponse> updateResource(CommentRequest commentRequest, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Comment"));
        BeanUtils.copyProperties(commentRequest, comment, serviceUtils.getNullPropertyNames(commentRequest));
        Comment savedComment = commentRepository.save(comment);
        CommentResponse response = CommentResponse.builder().build();
        BeanUtils.copyProperties(savedComment, response);
        return new RequestResponse<CommentResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<CommentResponse> deleteByResourceId(Long commentId) {
        if(commentRepository.existsById(commentId)){
            commentRepository.deleteById(commentId);
            return new RequestResponse<CommentResponse>(true, null, null);
        } else {
            throw new ResourceNotFoundException("Resource not found for given Id", "Comment");
        }
    }

    @Override
    public RequestResponse<PostResponse> getRelatedResourcesByResourceId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Comment"));
        Post post = Optional.of(comment.getPost()).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Post"));
        PostResponse response = PostResponse.builder().authorId(post.getAuthor().getId()).build();
        BeanUtils.copyProperties(post, response);
        return new RequestResponse<PostResponse>(true, List.of(response), null);
    }
}
