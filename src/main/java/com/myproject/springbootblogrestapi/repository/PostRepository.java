package com.myproject.springbootblogrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.springbootblogrestapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
