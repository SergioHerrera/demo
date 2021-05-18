package com.rindus.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.demo.model.Comment;
import com.rindus.demo.model.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sergio Herrera Ruiz.
 *
 */
@Service
@RequiredArgsConstructor
public class PostService {

	/**
	 * The RestTemplate.
	 */
	private final RestTemplate restTemplate;

	/**
	 * API endpoint.
	 */
	@Value("${resources.post.url}")
	private String postResource;

	/**
	 * Find all the posts.
	 * 
	 * @return a collection with all the posts.
	 */
	public List<Post> findAll() {
		return Arrays.stream(restTemplate.getForObject(postResource, Post[].class)).collect(Collectors.toList());
	}

	/**
	 * Find a post by Id.
	 * 
	 * @param post
	 * @return the created post.
	 */
	public Post findById(Long id) {
		return restTemplate.getForObject(postResource + "/" + id, Post.class);
	}

	/**
	 * Create a post.
	 * 
	 * @param post
	 * @return the created post.
	 */
	public Post create(Post post) {
		return restTemplate.postForObject(postResource, post, Post.class);
	}

	/**
	 * Update a post.
	 * 
	 * @param id
	 * @param post
	 * @return the updated post.
	 */
	public Post update(Long id, Post post) {

		return restTemplate.exchange(postResource + "/" + id, HttpMethod.PUT, new HttpEntity<>(post), Post.class, id)
				.getBody();

	}

	/**
	 * Delete a post using its internal identifier.
	 * 
	 * @param id
	 */
	public void delete(Long id) {

		restTemplate.delete(postResource + "/" + id, id);
	}

	/**
	 * Find a post with the comments.
	 * 
	 * @param post
	 * @return the created post.
	 */
	public Post getPostWithComments(Long id) {

		// Get the post.
		Post currentPost = restTemplate.getForObject(postResource + "/" + id, Post.class);

		// Get the post`s comments.
		Comment[] postComments = restTemplate.getForObject(postResource + "/" + id + "/comments", Comment[].class);

		// Set the comments to the post.
		currentPost.setComments(Arrays.asList(postComments));

		return currentPost;

	}
}
