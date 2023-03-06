package com.myproject.springbootblogrestapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myproject.springbootblogrestapi.dtos.PostDto;
import com.myproject.springbootblogrestapi.dtos.PostResponse;
import com.myproject.springbootblogrestapi.entity.Post;
import com.myproject.springbootblogrestapi.exception.ResourceNotFoundException;
import com.myproject.springbootblogrestapi.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	private PostRepository postRepository;
	private ModelMapper mapper;
	//private PostMapper postMapper;

	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
	//Post post = postMapper.mapToEntity(postDto);    the three top works with mapper
	//Post savedPost = postRepository.save(post);
	//PostDto postDtoResponse = postMapper.mapToDto(savedPost);
		// Convert DTO to Post
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		        Post post = mapToEntity(postDto);     //---The indented three line also work with the two private methods.

		        Post savedPost = postRepository.save(post);
		        PostDto postDtoResponse = mapToDto(savedPost);
//		PostDto postResponse = new PostDto();
//		
//		//convert Post to PostDto
//		postResponse.setId(savedPost.getId());
//		postResponse.setTitle(savedPost.getTitle());
//		postResponse.setDescription(savedPost.getDescription());
//		postResponse.setContent(savedPost.getContent());
		return postDtoResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
// Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(sortBy));	
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);	
		Page<Post> posts = postRepository.findAll(pageable);
		// get content from page object
		List<Post> listOfPost = posts.getContent();
//		List<PostDto> postDtos = new ArrayList<>();
//		for(Post eachPost: posts) {
//			postDtos.add(mapToDto(eachPost));
//		return postDtos;
//		}
		List<PostDto> content = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;

	}

	private PostDto mapToDto(Post post) {
		
		PostDto postDto = mapper.map(post, PostDto.class);
		
		
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());

		return postDto;
	}

	private Post mapToEntity(PostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePostById(long id, PostDto postDto) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public String deletePost(long id) {
		postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.deleteById(id);
		return "Post Id: " + id + " Deleted succesfully!";
	}
}
