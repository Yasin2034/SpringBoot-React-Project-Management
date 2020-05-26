package com.example.reactproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.reactproject.domain.User;
import com.example.reactproject.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if(user == null ) throw new UsernameNotFoundException("User not found");
		return user;
	}
	
	public User loadUserById(Long id) {
		User user = userRepository.getById(id);
		if(user == null) throw new UsernameNotFoundException("User not found");
		return user;
	}

	
	
	
}
