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
	
	@NotNull
	private Integer userId;
	
	private Integer id;
	
	@NotNull
	private String title;
	
	@NotNull
	private String body;
	
	private List<Comment> comments;

}
