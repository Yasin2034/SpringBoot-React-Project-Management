package com.example.reactproject.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.reactproject.domain.User;


@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if(user.getPassword() != null && user.getPassword().length()<6) {
			errors.rejectValue("password", "Length","Password must be at least 6 characters");
		}
		if(user.getPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("ConfirmPassword","Match","Passwords must match");
		}
		
	}

	
}
