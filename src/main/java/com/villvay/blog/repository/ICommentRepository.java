package com.villvay.blog.repository;

import com.villvay.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findAllByPost_Id(Long postId);
}
