package com.villvay.blog.repository;

import com.villvay.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

    Set<Post> getAllPostsByAuthorId(Long authorId);

    Optional<Post> findByComments_Id(Long commentId);
}
