package com.example.reactproject.payload;

public class JWTLoginSuccessResponse {

	private boolean success;
	private String token;
	
	public JWTLoginSuccessResponse(boolean success,String token) {
		this.success = success;
		this.token = token;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JWTLoginSuccessResponse [success=");
		builder.append(success);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
