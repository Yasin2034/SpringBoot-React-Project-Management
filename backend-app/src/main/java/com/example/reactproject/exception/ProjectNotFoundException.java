package com.example.reactproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException{

	public ProjectNotFoundException(String message) {
		super(message);
	}
	
}
