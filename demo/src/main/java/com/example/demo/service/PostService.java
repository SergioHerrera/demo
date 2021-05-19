package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

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
	public static final String SUCCESS_DESCRIPTION = "ok";

	/**
	 * Default failed description.
	 */
	public static final String FAILED_DESCRIPTION = "Something went wrong.";

	/**
	 * Get a post by using its ID.
	 * 
	 * @param postId
	 * @return Output object with the result.
	 */
	public Output getPost(Long postId) {

		//Build the URL
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();

		//Build a default object with failed status.
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest)
				.build();

		try {

			
			//Request
			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);

			//Set success status.
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
	 * 
	 * @return Output object with the result.
	 */
	public Output getPosts() {

		//Build the URL
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).build()
				.toUriString();

		//Build a default object with failed status.
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest)
				.build();

		try {

			//Request
			Post[] posts = restTemplate.getForObject(getPostRequest, Post[].class);

			//Set success status.
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
	 * 
	 * @return Output object with the result.
	 */
	public Output newPost(Post post) {

		//Build the URL
		final String newPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE).build()
				.toUriString();

		//Build a default object with failed status.
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(newPostRequest)
				.build();

		try {

			//Request
			final Post newPost = restTemplate.postForObject(newPostRequest, post, Post.class);

			//Set success status.
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
	 * 
	 * @return Output object with the result.
	 */
	public Output updatePost(Long postId, Post post) {

		//Build the URL
		final String updatePostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();

		//Build a default object with failed status.
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(updatePostRequest)
				.build();

		try {

			//Request
			restTemplate.put(updatePostRequest, post);

			//Set success status.
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);

		} catch (RestClientException e) {

			log.error("Something went wrong updating post: {} - {}", postId, e.getMessage());
		}

		return output;

	}

	/**
	 * Delete a new post
	 * 
	 * @return Output object with the result.
	 */
	public Output deletePost(Long postId) {

		//Build the URL
		final String deletePostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();

		//Build a default object with failed status.
		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(deletePostRequest)
				.build();

		try {

			//Request
			restTemplate.delete(deletePostRequest);

			//Set success status.
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);

		} catch (RestClientException e) {

			log.error("Something went wrong deleting post: {} - {}", postId, e.getMessage());
		}

		return output;

	}

	/**
	 * Get a post by using its ID with its comments attached.
	 * 
	 * @param postId
	 * @return Output object with the result.
	 */
	public Output getPostWithComments(Long postId) {

		//Get posts and comments.
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();
		final String getPostCommentsRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).pathSegment("comments").build().toUriString();

		Output output = Output.builder().success(false).description(FAILED_DESCRIPTION).resourceUrl(getPostRequest)
				.build();

		try {

			// Get the current post data.
			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);

			// Get the post`s comments.
			Comment[] postComments = restTemplate.getForObject(getPostCommentsRequest, Comment[].class);

			// Set the comments to the post.
			currentPost.setComments(Arrays.asList(postComments));

			// Set the success response.
			output.setContent(Arrays.asList(currentPost));
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);

		} catch (RestClientException e) {

			log.error("Something went wrong retrieving post: {} - {}", postId, e.getMessage());
		}

		return output;

	}



	/**
	 * Store a post as XML.
	 * @param postId
	 * @return Output object with success status if went well with failed status if not.
	 */
	public Output storeAsXml(Long postId) {
		
		Output output = new Output(null, false, FAILED_DESCRIPTION, null);

		//Get the post
		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();

		try {

			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);

			final String postAsXML = storeAsXML.converter(currentPost);
			
			log.info("Post as XML: {}", postAsXML);
			
			storeAsXML.store(currentPost.getId() + "-XML-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".xml" , postAsXML);
			
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			

		} catch (Exception e) {

			log.error("Something went wrong storing post as XML: ", e.getMessage());
		}

		return output;
	}
	
	/**
	 * Store a post as JSON.
	 * @param postId
	 * @return Output object with success status if went well with failed status if not.
	 */
	public Output storeAsJson(Long postId) {
		
		Output output = new Output(null, false, FAILED_DESCRIPTION, null);

		final String getPostRequest = UriComponentsBuilder.fromHttpUrl(fakeApiUrl).pathSegment(RESOURCE)
				.pathSegment(postId.toString()).build().toUriString();

		try {

			Post currentPost = restTemplate.getForObject(getPostRequest, Post.class);

			final String postAsXML = storeAsJSON.converter(currentPost);
			
			log.info("Post as JSON: {}", postAsXML);
			
			storeAsJSON.store(currentPost.getId() + "-JSON-" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + ".json" , postAsXML);
			
			output.setSuccess(true);
			output.setDescription(SUCCESS_DESCRIPTION);
			

		} catch (Exception e) {

			log.error("Something went wrong storing post as JSON: ", e.getMessage());
		}

		return output;
	}

}
