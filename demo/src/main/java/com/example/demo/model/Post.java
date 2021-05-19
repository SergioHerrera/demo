package com.example.demo.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
	
	@NotNull(message = "user cannot be null")
	private Integer userId;
	
	private Integer id;
	
	@NotNull(message = "title cannot be null")
	private String title;
	
	@NotNull(message = "body cannot be null")
	private String body;
	
	private List<Comment> comments;

}
