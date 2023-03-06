package com.myproject.springbootblogrestapi.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.springbootblogrestapi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Set<Comment> findByPostId(long postId);
	Optional<Comment> findByPostIdAndId(long postId, long id);
}
