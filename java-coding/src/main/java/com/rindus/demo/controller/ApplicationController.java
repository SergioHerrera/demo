package com.rindus.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.rindus.demo.model.Post;
import com.rindus.demo.service.PostService;

@Controller
public class ApplicationController {


	@Autowired
	private PostService postService;
	
	
	@ExceptionHandler({Exception.class})
	public String exceptionHandler(Exception e, Model model) {
		
		model.addAttribute("error", e.getMessage());
		return index(model);
	}


	@GetMapping({"/","/post"})
	public String index(Model model) {
		
		model.addAttribute("allPosts", postService.findAll());
		return "index";
	}
	
	@GetMapping({"/details/{id}","/post/details/{id}"})
	public String details(Model model, @PathVariable(name = "id", required = true) Long postId) {
		
		
		model.addAttribute("post", postService.getPostWithComments(postId));
		return "view-post";
	}
	
	@GetMapping({"/create","/post/create"})
	public String create(Model model) {
		
		
		postService.create(new Post(101, 1, "Title example", "Body example", null));
		
		model.addAttribute("message", "Post successfully created!");
		
		return "info-view";
	}
	
	@GetMapping({"/update/{id}","/post/update"})
	public String update(Model model) {
		
		//500
		postService.update(101l, new Post(101, 1, "Updated title", "Updated body", null));
		
		//postService.update(1l, new Post(1, 1, "Updated title", "Updated body", null));
		
		
		model.addAttribute("message", "Post successfully updated!");
		
		return "info-view";
	}
	
	@GetMapping({"/delete/{id}","/post/delete/{id}"})
	public String delete(Model model, @PathVariable(name = "id", required = true) Long postId) {
		
		
		postService.delete(postId);
		
		model.addAttribute("message", "Post successfully deleted!");
		
		return "info-view";
	}
}
