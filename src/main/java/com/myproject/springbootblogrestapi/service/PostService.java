package com.myproject.springbootblogrestapi.service;

import com.myproject.springbootblogrestapi.dtos.PostDto;
import com.myproject.springbootblogrestapi.dtos.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto);

	PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

	PostDto getPostById(long id);

	PostDto updatePostById(long id, PostDto postDto);

	String deletePost(long id);

}
