package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreAsJSONService implements StoreService {


	/**
	 * Stores a Post in JSON format into a file.
	 */
	@Override
	public void store(Post postToStore) {
		
		final ObjectMapper mapper = new ObjectMapper();

		try {

			final String postAsJson = mapper.writeValueAsString(postToStore);

			log.info("Storing post as JSON: {}", postAsJson);

		} catch (Exception e) {

			log.error("Something went wrong storing the post as JSON: {}", e.getMessage());

		}

	}

}
