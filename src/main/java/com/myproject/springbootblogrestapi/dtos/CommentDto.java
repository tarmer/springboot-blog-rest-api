package com.myproject.springbootblogrestapi.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
@ApiModel(description="Comment Model")
@Setter
@Getter
public class CommentDto {
	@ApiModelProperty(value="Blog Post comment id")
	private long id;
	
	@NotEmpty(message ="name can not be empty or null")
	@ApiModelProperty(value="Blog Post comment name")
	private String name;
	
    //email field validation
	//Email should not be null or empty
	@Email
	@NotEmpty(message="Email can not be empty or null")
	@ApiModelProperty(value="Blog Post comment email")
	private String email;
	
	//Comment body should not be null or empty
	//comment body should be a minimum of 10 characters
	@NotEmpty(message="Comment body can not be empty or null")
	@Size(min=10, message="Comment body must be a minimum of 10 chracters")
	@ApiModelProperty(value="Blog Post comment body")
	private String body;
}
