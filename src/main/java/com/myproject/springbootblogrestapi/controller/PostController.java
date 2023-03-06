package com.myproject.springbootblogrestapi.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.springbootblogrestapi.dtos.PostDto;
import com.myproject.springbootblogrestapi.dtos.PostResponse;
import com.myproject.springbootblogrestapi.service.PostService;
import com.myproject.springbootblogrestapi.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="POST resource CRUD Rest APIs")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@ApiOperation(value="Create Post REST API")
	@PreAuthorize("hasRole('ADMIN')")
	// create blog post api
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@ApiOperation(value="Get all Posts REST API")
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
	{
		return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize,sortBy, sortDir));
	}

	@ApiOperation(value="Get Post by id REST API")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
		return ResponseEntity.ok(postService.getPostById(id));

	}

	@ApiOperation(value="Update Post by id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@PathVariable long id, @Valid @RequestBody PostDto postDto) {
		return ResponseEntity.ok(postService.updatePostById(id, postDto));
	}
	
	@ApiOperation(value="Delete Post by id REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id) {
		return ResponseEntity.ok(postService.deletePost(id));
	}
}
