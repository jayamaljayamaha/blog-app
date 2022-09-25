package com.villvay.blog.service.impl;

import com.villvay.blog.dto.request.PostRequest;
import com.villvay.blog.dto.response.CommentResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.entity.Author;
import com.villvay.blog.entity.Comment;
import com.villvay.blog.entity.Post;
import com.villvay.blog.exceptions.ResourceNotFoundException;
import com.villvay.blog.repository.IAuthorRepository;
import com.villvay.blog.repository.ICommentRepository;
import com.villvay.blog.repository.IPostRepository;
import com.villvay.blog.service.IResourceService;
import com.villvay.blog.utils.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("PostService")
public class PostService implements IResourceService<PostResponse, PostRequest, CommentResponse> {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public RequestResponse<PostResponse> getAllResources() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = posts.stream().map(post -> {
                    PostResponse response = PostResponse.builder().authorId(post.getAuthor().getId()).build();
                    BeanUtils.copyProperties(post, response);
                    return response;
                }
        ).toList();
        return new RequestResponse<PostResponse>(true, postResponses, null);
    }

    @Override
    public RequestResponse<PostResponse> getResourceById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Post"));
        PostResponse response = PostResponse.builder().authorId(post.getAuthor().getId()).build();
        BeanUtils.copyProperties(post, response);
        return new RequestResponse<PostResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<PostResponse> addResource(PostRequest postRequest) {
        Author author = authorRepository.findById(postRequest.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Resource not found for given Id", "Author"));
        Post post = Post.builder().author(author).body(postRequest.getBody()).title(postRequest.getTitle()).build();
        Post savedPost = postRepository.save(post);
        PostResponse response = PostResponse.builder().authorId(author.getId()).build();
        BeanUtils.copyProperties(savedPost, response);
        return new RequestResponse<PostResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<PostResponse> updateResource(PostRequest postRequest, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Id", "Post"));
        BeanUtils.copyProperties(postRequest, post, serviceUtils.getNullPropertyNames(postRequest));
        Post savedPost = postRepository.save(post);
        PostResponse response = PostResponse.builder().build();
        BeanUtils.copyProperties(savedPost, response);
        return new RequestResponse<PostResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<PostResponse> deleteByResourceId(Long postId) {
        if(postRepository.existsById(postId)){
            postRepository.deleteById(postId);
            return new RequestResponse<PostResponse>(true, null, null);
        } else {
            throw new ResourceNotFoundException("Resource not found for given Id", "Post");
        }
    }

    @Override
    public RequestResponse<CommentResponse> getRelatedResourcesByResourceId(Long postId) {
        Set<Comment> comments = commentRepository.findAllByPost_Id(postId);
        List<CommentResponse> responses = comments.stream().map(comment -> {
            CommentResponse response = CommentResponse.builder().postId(comment.getPost().getId()).build();
            BeanUtils.copyProperties(comment, response);
            return response;
        }).toList();
        return new RequestResponse<CommentResponse>(true, responses, null);
    }
}
