package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoreAsXMLService implements StoreService {



	/**
	 * Stores a Post in JSON format into a file.
	 */
	@Override
	public void store(Post postToStore) {
		
		final XmlMapper mapper = new XmlMapper();

		try {

			final String postAsXml = mapper.writeValueAsString(postToStore);

			log.info("Storing post as XML: {}", postAsXml);

		} catch (Exception e) {

			log.error("Something went wrong storing the post as XML: {}", e.getMessage());

		}

	}

}
