package com.iosapp.handler;

import org.springframework.stereotype.Service;

import com.iosapp.payload.LoginRequest;
import com.iosapp.payload.TokenResponse;
import com.iosapp.security.JWTUtil;
import com.iosapp.utilities.DefaultUserBuilderService;

import reactor.core.publisher.Mono;

@Service
public class AuthService {

	private final JWTUtil jwtUtil;
	private final DefaultUserBuilderService userRepository;
	
	public AuthService(JWTUtil jwtUtil, DefaultUserBuilderService userRepository) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}

	public Mono<TokenResponse> authenticate(LoginRequest loginRequest) {
		return this.userRepository
			.findByUsername(loginRequest.getUsername())
			.map(user -> new TokenResponse(jwtUtil.generateToken(user)));
	}
}
