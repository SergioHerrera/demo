package com.example.demo.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Comment;
import com.example.demo.model.Output;
import com.example.demo.model.Post;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sergio Herrera Ruiz.
 * Service in charge of consuming the FakeApi data.
 */

@Service
@Slf4j
public class PostService {

	//https://jsonplaceholder.typicode.com/posts
	
	/**
	 * Base API URL.
	 */
	@Value("${endpoint.fakeapi}")
	private String fakeApiUrl;
	
	/**
	 * The RestTemplate.
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Store as XML service.
	 */
	@Autowired
	private StoreAsJSONService storeAsJSON;
	
	/**
	 * Store as XML service.
	 */
	@Autowired
	private StoreAsXMLService storeAsXML;
	
	/**
	 * The resource.
	 */
	private static final String RESOURCE = "posts";
	
	/**
	 * Default success description.
	 */
	private static final String SUCCESS_DESCRIPTION = "ok";
	
	/**
	 * Default failed description.
	 */
	private static final String FAILED_DESCRIPTION = "Something went wrong.";
	
	
	/**
	 * Get a post by using its ID.
	 * @param postId
	 * @return Output object with the result.
	 */
	public Output getPost(Integer postId) {
		
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).pathSegment(postId.toString()).build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest).build();
		
		try {
			
			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);
			
			output.setContent(Arrays.asList(currentPost));
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong retrieving post: {} - {}", postId, e.getMessage());
		}
		
		return output;
		
	}
	
	/**
	 * Get a Post`s collection.
	 * @return Output object with the result.
	 */
	public Output getPosts() {
		
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest).build();
		
		try {
			
			Post[] posts = restTemplate.getForObject(getPostRequest, Post[].class);
			
			output.setContent(Arrays.asList(posts));
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong retrieving posts - {}", e.getMessage());
		}
		
		return output;
		
	}
	
	/**
	 * Create a new post
	 * @return Output object with the result.
	 */
	public Output newPost(Post post) {
		
		final String newPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(newPostRequest).build();
		
		try {
			
			Post newPost = restTemplate.postForObject(newPostRequest, post, Post.class);
			
			output.setContent(Arrays.asList(newPost));
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong creating a new post - {}", e.getMessage());
		}
		
		return output;
		
	}
	
	/**
	 * Update post
	 * @return Output object with the result.
	 */
	public Output updatePost(Integer postId, Post post) {
		
		final String updatePostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).pathSegment(postId.toString()).build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(updatePostRequest).build();
		
		try {
			
			restTemplate.put(updatePostRequest, post);
			
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong updating post: {} - {}", postId, e.getMessage());
		}
		
		return output;
		
	}
	
	/**
	 * Delete a new post
	 * @return Output object with the result.
	 */
	public Output deletePost(Integer postId) {
		
		final String deletePostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).pathSegment(postId.toString()).build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(deletePostRequest).build();
		
		try {
			
			restTemplate.delete(deletePostRequest);
			
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong deleting post: {} - {}", postId, e.getMessage());
		}
		
		return output;
		
	}
	
	/**
	 * Get a post by using its ID with its comments attached.
	 * @param postId
	 * @return Output object with the result.
	 */
	public Output getPostWithComments(Integer postId) {
		
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).pathSegment(postId.toString()).build().toUriString();
		final String getPostCommentsRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).pathSegment(postId.toString()).pathSegment("comments").build().toUriString();
		
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest).build();
		
		try {
			
			//Get the current post data.
			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);
			
			//Get the post`s comments.
			Comment[] postComments = restTemplate.getForObject(getPostCommentsRequest, Comment[].class);
			
			//Set the comments to the post.
			currentPost.setComments(Arrays.asList(postComments));
			
			output.setContent(Arrays.asList(currentPost));
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			
		} catch (RestClientException e) {
			
			log.error("Something went wrong retrieving post: {} - {}", postId, e.getMessage());
		}
		
		return output;
		
	}
	
	@PostConstruct
	public void test() {
		
		Post p = new Post(1, 1, "test", "body", null);
		
		storeAsXML.store(p);
		
		storeAsJSON.store(p);
		
	}
	
}
