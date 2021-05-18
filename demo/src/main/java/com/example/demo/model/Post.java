package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
	
	private Integer userId;
	
	private Integer id;
	
	private String title;
	
	private String body;
	
	private List<Comment> comments;

}
