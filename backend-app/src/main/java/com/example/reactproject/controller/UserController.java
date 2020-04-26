package com.example.reactproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactproject.domain.User;
import com.example.reactproject.service.MapValidationService;
import com.example.reactproject.service.UserService;
import com.example.reactproject.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationService MapValidationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult result){
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = MapValidationService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	
	
}
