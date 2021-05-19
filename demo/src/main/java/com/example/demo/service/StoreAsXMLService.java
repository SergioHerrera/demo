package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreAsXMLService extends StoreService {

	/**
	 * Transform the post into a XML string.
	 * 
	 * @throws JsonProcessingException
	 */
	@Override
	public String converter(Post postToStore) throws JsonProcessingException {

		final XmlMapper mapper = new XmlMapper();

		return mapper.writeValueAsString(postToStore);

	}

}
