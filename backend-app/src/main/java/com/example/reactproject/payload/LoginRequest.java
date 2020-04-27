package com.example.reactproject.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "Username can not blank")
	private String username;
	@NotBlank(message = "Password can not blank")
	private String password;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
