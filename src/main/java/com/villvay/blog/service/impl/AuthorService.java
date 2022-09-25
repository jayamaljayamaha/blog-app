package com.villvay.blog.service.impl;

import com.villvay.blog.dto.request.AuthorRequest;
import com.villvay.blog.dto.response.AuthorResponse;
import com.villvay.blog.dto.response.PostResponse;
import com.villvay.blog.dto.response.RequestResponse;
import com.villvay.blog.entity.Author;
import com.villvay.blog.entity.Post;
import com.villvay.blog.exceptions.ResourceNotFoundException;
import com.villvay.blog.repository.IAuthorRepository;
import com.villvay.blog.repository.IPostRepository;
import com.villvay.blog.service.IResourceService;
import com.villvay.blog.utils.ServiceUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service("AuthorService")
public class AuthorService implements IResourceService<AuthorResponse, AuthorRequest, PostResponse> {

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Override
    public RequestResponse<AuthorResponse> getAllResources() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorResponse> authorResponses = authorList.stream().map(author -> {
            AuthorResponse response = AuthorResponse.builder().build();
            BeanUtils.copyProperties(author, response);
            return response;
        }).toList();
        return new RequestResponse<AuthorResponse>(true, authorResponses, null);
    }

    @Override
    public RequestResponse<AuthorResponse> getResourceById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found for given Id", "Author"));
        AuthorResponse response = AuthorResponse.builder().build();
        BeanUtils.copyProperties(author, response);
        return new RequestResponse<AuthorResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<AuthorResponse> addResource(AuthorRequest authorRequest) {
        Author author = Author.builder().build();
        BeanUtils.copyProperties(authorRequest, author);
        Author savedAuthor = authorRepository.save(author);
        AuthorResponse response = AuthorResponse.builder().build();
        BeanUtils.copyProperties(savedAuthor, response);
        return new RequestResponse<AuthorResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<AuthorResponse> updateResource(AuthorRequest authorRequest, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found for given Id", "Author"));
        BeanUtils.copyProperties(authorRequest, author, serviceUtils.getNullPropertyNames(authorRequest));
        Author savedAuthor = authorRepository.save(author);
        AuthorResponse response = AuthorResponse.builder().build();
        BeanUtils.copyProperties(savedAuthor, response);
        return new RequestResponse<AuthorResponse>(true, List.of(response), null);
    }

    @Override
    public RequestResponse<AuthorResponse> deleteByResourceId(Long authorId) {
        if(authorRepository.existsById(authorId)){
            authorRepository.deleteById(authorId);
            return new RequestResponse<AuthorResponse>(true, null, null);
        } else {
            throw new ResourceNotFoundException("Author not found for given Id", "Author");
        }

    }

    @Override
    public RequestResponse<PostResponse> getRelatedResourcesByResourceId(Long authorId) {
        Set<Post> posts = postRepository.getAllPostsByAuthorId(authorId);
        List<PostResponse> responses = posts.stream().map(post -> {
            PostResponse response = PostResponse.builder().authorId(post.getAuthor().getId()).build();
            BeanUtils.copyProperties(post, response);
            return response;
        }).toList();
        return new RequestResponse<PostResponse>(true, responses, null);
    }


}
