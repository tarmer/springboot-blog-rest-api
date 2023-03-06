package com.myproject.springbootblogrestapi.dtos;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDto2 {
	private long id;
	
	//title should not be empty or null
	//title should have atleast two characters
	@NotEmpty
	@Size(min = 2 , message ="Post Title should have at least two characters")
	private String title;
	
	//description should not be empty or null
	//description should have atleast 10 characters
	@NotEmpty
	@Size(min = 2 , message ="Post description should have at least 10 characters")
	private String description;
	
	//content should not be empty or null
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;

}
