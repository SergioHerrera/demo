package com.rindus.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Comment {
	
	private Integer postId;
	
	private Integer id;
	
	private String name;
	
	private String email;
	
	private String body;

}
