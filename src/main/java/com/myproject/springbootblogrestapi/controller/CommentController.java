package com.myproject.springbootblogrestapi.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.springbootblogrestapi.dtos.CommentDto;
import com.myproject.springbootblogrestapi.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="Comment resource CRUD Rest APIs")
@RestController
@RequestMapping("/api/v1/posts/")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@ApiOperation(value="Create Comment REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<>(commentService.createComment(postId,commentDto),HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Get all Comments REST API")
	@GetMapping("{postId}/comments")
	public ResponseEntity<Set<CommentDto>> getAllCommentsByPostId(@PathVariable long postId){
		return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
	}
	
	@ApiOperation(value="Get Comment by id REST API")
	@GetMapping("/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long commentId,@PathVariable long postId) {
		return ResponseEntity.ok(commentService.getCommentById(commentId, postId));
}   
	@ApiOperation(value="Update comment by id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentDto commentRequest ) {
		return ResponseEntity.ok(commentService.updateCommentById(postId,commentId,commentRequest));
	}
	@ApiOperation(value="Delete Comment by id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId) {
		return ResponseEntity.ok(commentService.deleteCommentById(postId,commentId));
	}
}
