package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreAsJSONService extends StoreService {

	/**
	 * Transform the Post into a JSON string.
	 * 
	 * @throws JsonProcessingException
	 */
	@Override
	public String converter(Post postToStore) throws JsonProcessingException {

		final ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(postToStore);

	}

}
