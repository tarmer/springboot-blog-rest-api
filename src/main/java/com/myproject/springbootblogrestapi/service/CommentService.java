package com.myproject.springbootblogrestapi.service;

import java.util.Set;

import com.myproject.springbootblogrestapi.dtos.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);

	Set<CommentDto> getAllCommentsByPostId(long postId);

	CommentDto getCommentById(long commentId, long postId);

	CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest);

	String deleteCommentById(long postId, long commentId);
}