package com.myproject.springbootblogrestapi.entity;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="comments")
public class Comment {
	@Id
	 @GeneratedValue(strategy= GenerationType.IDENTITY)
	 private Long id;
	 
	 private String name;
	 private String email;
	 private String body;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="post_id",nullable =false)
	 private Post post;
}
