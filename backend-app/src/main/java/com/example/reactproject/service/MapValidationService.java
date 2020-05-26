package com.example.reactproject.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationService {

	public ResponseEntity<?> mapValidationService(BindingResult result) {
		if (result.hasErrors()) {
			HashMap<String, String> errorMap = new HashMap<>();

			for (FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<HashMap<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
}
