package com.example.reactproject.service.Impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.User;
import com.example.reactproject.exception.UsernameAlreadyExistsException;
import com.example.reactproject.repository.UserRepository;
import com.example.reactproject.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User saveUser(User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setUsername(newUser.getUsername());
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username: " + newUser.getUsername()+" already exists");
		}
	}
	
	
	
	
}
