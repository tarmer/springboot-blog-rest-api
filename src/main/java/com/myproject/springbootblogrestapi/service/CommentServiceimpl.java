package com.myproject.springbootblogrestapi.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.myproject.springbootblogrestapi.dtos.CommentDto;
import com.myproject.springbootblogrestapi.entity.Comment;
import com.myproject.springbootblogrestapi.entity.Post;
import com.myproject.springbootblogrestapi.exception.BlogAPIException;
import com.myproject.springbootblogrestapi.exception.ResourceNotFoundException;
import com.myproject.springbootblogrestapi.repository.CommentRepository;
import com.myproject.springbootblogrestapi.repository.PostRepository;

@Service
public class CommentServiceimpl implements CommentService {
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceimpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;

	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;

	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = mapToEntity(commentDto);
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		return mapToDto(savedComment);
	}

	@Override
	public Set<CommentDto> getAllCommentsByPostId(long postId) {

		Set<Comment> comments = commentRepository.findByPostId(postId);
		Set<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toSet());
		return commentDtos;
//		Post post = postRepository.findById(postId)
//				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
//		Set<Comment> comments = post.getComments();
//		Set<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toSet());
//		return commentDtos;
	}

	@Override
	public CommentDto getCommentById(long commentId, long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Does not belong to post");
		}
		return mapToDto(comment);
		
		//Method 2 of doing it 
//		Optional<Comment> comment = commentRepository.findByPostIdAndId(postId, commentId);
//		if(comment.isEmpty()) {
//			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Is Not Found for the associated post");
//		}
//		return mapToDto(comment.get());
	}

	@Override
	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Does not belong to post");
		}
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		Comment savedComment = commentRepository.save(comment);
		return mapToDto(savedComment);
	}

	@Override
	public String deleteCommentById(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment Does not belong to post");
		}
		
		commentRepository.deleteById(commentId);
		return "Comment with Id: "+ commentId + "Deleted Succesfully";
	}
}
