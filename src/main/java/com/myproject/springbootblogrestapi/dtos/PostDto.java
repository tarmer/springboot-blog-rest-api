package com.myproject.springbootblogrestapi.dtos;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description="Post Model")
@Setter
@Getter
public class PostDto {
	@ApiModelProperty(value="Blog Post id")
	private long id;
	
	//title should not be empty or null
	//title should have atleast two characters
	@NotEmpty
	@Size(min = 2 , message ="Post Title should have at least two characters")
	@ApiModelProperty(value="Blog Post title")
	private String title;
	
	//description should not be empty or null
	//description should have atleast 10 characters
	@NotEmpty
	@Size(min = 2 , message ="Post description should have at least 10 characters")
	@ApiModelProperty(value="Blog Post description")
	private String description;
	
	//content should not be empty or null
	@NotEmpty
	@ApiModelProperty(value="Blog Post content")
	private String content;
	
	@ApiModelProperty(value="Blog Post comments")
	private Set<CommentDto> comments;

}
