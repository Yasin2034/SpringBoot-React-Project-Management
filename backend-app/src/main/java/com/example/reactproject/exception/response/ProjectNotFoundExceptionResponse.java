package com.example.reactproject.exception.response;

import lombok.Data;

@Data
public class ProjectNotFoundExceptionResponse {
	
	private String projectNotFound;
	
	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		this.projectNotFound=projectNotFound;
	}
	
	

}
