package com.example.reactproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactproject.domain.User;
import com.example.reactproject.payload.JWTLoginSuccessResponse;
import com.example.reactproject.payload.LoginRequest;
import com.example.reactproject.security.JwtTokenProvider;
import com.example.reactproject.service.IMapValidationService;
import com.example.reactproject.service.IUserService;
import com.example.reactproject.validator.UserValidator;
import static com.example.reactproject.security.SecurityConstants.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private IMapValidationService MapValidationService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,BindingResult result){
		ResponseEntity<?> errorMap = MapValidationService.mapValidationService(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), 
				loginRequest.getPassword()
				));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX+jwtTokenProvider.generatetoken(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}
	

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult result){
		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = MapValidationService.mapValidationService(result);
		if(errorMap != null) return errorMap;
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	
	
}
