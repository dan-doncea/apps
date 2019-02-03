package com.iosapp.payload;

public class TokenResponse {
	
	private String token;

	public TokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() { 
		return "TokenResponse [token=" + token + "]";
	}
	
	
}
