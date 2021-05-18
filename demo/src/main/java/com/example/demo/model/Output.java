package com.example.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Output {

	private String resourceUrl;
	
	private boolean success;
	
	private String description;
	
	private List<Post> content;
	
}
