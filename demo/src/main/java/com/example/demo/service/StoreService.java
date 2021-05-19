package com.example.demo.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.example.demo.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class StoreService {

	public abstract String converter(Post postToStore) throws JsonProcessingException;
	
	public void store(String filename, String data) throws IOException {
		
		
		FileWriter fileWriter = new FileWriter("src/main/resources/data/" + filename);
		
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.print(data);
	    printWriter.close();
		
	}
	
}
