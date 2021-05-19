package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Output;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;

/**
 * @author Sergio Herrera Ruiz
 *
 */
@RestController
public class PostController {
	
	
	/**
	 * The Post Service.
	 */
	@Autowired
	private PostService postService;
	
	/**
	 * Catch exceptions like invalid Post inputs and show our custom output.
	 * @param e
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public Output exceptionHandler(Exception e) {
		
		
		return new Output(null, false, e.getMessage(), null);
	}
	
	
	/**
	 * Get all posts
	 * @return Output object.
	 */
	@GetMapping(value = {"/", ""}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Output getAll(){
		
		
		return postService.getPosts();
	}
	
	
	/**
	 * Get one post.
	 * @param postId
	 * @return Output object.
	 */
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output getOne(@PathVariable(name = "id", required = true) Long postId){
		
		
		return postService.getPost(postId);
	}
	
	/**
	 * Get one post with the comments.
	 * @param postId
	 * @return
	 */
	@GetMapping(value = "/{id}/comments", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output getOneWithComments(@PathVariable(name = "id", required = true) Long postId){
		
		
		return postService.getPostWithComments(postId);
	}
	
	/**
	 * Create a new post.
	 * @param post
	 * @return
	 */
	@PostMapping(value = {"/", ""}, produces=MediaType.APPLICATION_JSON_VALUE)
	public Output createPost(@RequestBody @Valid Post post){
		
		
		return postService.newPost(post);
	}
	
	/**
	 * Update a post.
	 * @param postId
	 * @param post
	 * @return
	 */
	@PutMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output updatePost(@PathVariable(name = "id", required = true) Long postId, @RequestBody Post post){
		
		
		return postService.updatePost(postId, post);
	}
	
	/**
	 * Delete a post.
	 * @param postId
	 * @return
	 */
	@DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output deletePost(@PathVariable(name = "id", required = true) Long postId){
		
		
		return postService.deletePost(postId);
	}
	
	/**
	 * Store a post in JSON format.
	 * @param postId
	 * @return
	 */
	@GetMapping(value = "/{id}/json", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output storeAsJson(@PathVariable(name = "id", required = true) Long postId){
		
		
		return postService.storeAsJson(postId);
	}
	
	/**
	 * Store a Post in XML format.
	 * @param postId
	 * @return
	 */
	@GetMapping(value = "/{id}/xml", produces=MediaType.APPLICATION_JSON_VALUE)
	public Output storeAsXml(@PathVariable(name = "id", required = true) Long postId){
		
		
		return postService.storeAsXml(postId);
	}
	
	
}
