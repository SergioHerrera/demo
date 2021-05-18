package com.rindus.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PostService postService;

	@Test
	void testFindAll() {
		
		//Employee emp = new Employee(“E001”, "Eric Simmons");
		
		
		
//        Mockito
//          .when(restTemplate.getForObject(
//            “http://localhost:8080/employee/E001”, Employee.class))
//          .thenReturn(new ResponseEntity(emp, HttpStatus.OK));
//
//        Employee employee = empService.getEmployee(id);
//        
//        
//        Assert.assertEquals(emp, employee);
//		
//		
//		List<Post> list = postService.findAll();
//
//		assertThat(list, is(not(empty())));
//		assertThat(list.size(), is(5000));

	}

}
