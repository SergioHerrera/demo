package com.example.demo;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Output;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PostService postService;

	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(postService, "fakeApiUrl", "https://jsonplaceholder.typicode.com");
	}
	
	@Test
	void testGetList() {
	
		Post post1 = new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
				"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
				null);
		
		Post post2 = new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
				"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
				null);
		
		Post posts[] = new Post[] {post1, post2};
		

		Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class))
				.thenReturn(posts);

		Output output = postService.getPosts();

		Assertions.assertEquals(output.isSuccess(), true);
		Assertions.assertEquals(posts.length, output.getContent().size());

	}

	@Test
	void testGetOne() {
	
		Post post = new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
				"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
				null);

		Mockito.when(restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", Post.class))
				.thenReturn(post);

		Output output = postService.getPost(1l);

		Assertions.assertEquals(output.isSuccess(), true);
		Assertions.assertEquals(post, output.getContent().get(0));
		

	}
	
	@Test
	void testCreate() {
	
		Post post = new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
				"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
				null);

		Mockito.when(restTemplate.postForObject("https://jsonplaceholder.typicode.com/posts", post, Post.class))
				.thenReturn(post);

		Output output = postService.newPost(post);

		Assertions.assertEquals(output.isSuccess(), true);
		Assertions.assertEquals(post, output.getContent().get(0));
		
	}
	
	@Test
	void testUpdate() {
	
		Post post = new Post(1, 1, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
				"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto",
				null);

	

		Output output = postService.updatePost(1l, post);

		verify(restTemplate).put("https://jsonplaceholder.typicode.com/posts/1", post);
		Assertions.assertEquals(output.isSuccess(), true);
		
	}
	
	@Test
	void testDelete() {
	
		Output output = postService.deletePost(1l);

		verify(restTemplate).delete("https://jsonplaceholder.typicode.com/posts/1");
		Assertions.assertEquals(output.isSuccess(), true);
		
	}
	

}
