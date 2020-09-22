package com.example.reactproject.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IMapValidationService {

	ResponseEntity<?> mapValidationService(BindingResult result);
}
