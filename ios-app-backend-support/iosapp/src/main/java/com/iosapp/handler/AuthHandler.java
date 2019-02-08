package com.iosapp.handler;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.payload.LoginRequest;
import com.iosapp.payload.TokenResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

	@Autowired
	private final AuthService authService;
	
	public AuthHandler(AuthService authService) {
		this.authService = authService;
	}
	
//	public Mono<ServerResponse> login(ServerRequest request) {	
//		return request.bodyToMono(LoginRequest.class)
//				.flatMap(lr -> userRepository.findByUsername(lr.getUsername())
//						.map(user -> new TokenResponse(jwtUtil.generateToken(user)) ))
//				.flatMap(tok -> ServerResponse.ok().body(Mono.just(tok), TokenResponse.class))
//				.switchIfEmpty(ServerResponse.badRequest().body(Mono.just(new BadRequestException("Incorrect username or password.")), BadRequestException.class));
//	}
	
	public Mono<ServerResponse> login(ServerRequest request) {	
		return authentificationReadResponse(
				request.bodyToMono(LoginRequest.class)
					.flatMap(lr -> this.authService.authenticate(lr))
					.switchIfEmpty(Mono.error(new AuthenticationException("Invalid username or password."))));
	}
	
    private static Mono<ServerResponse> authentificationReadResponse(Mono<TokenResponse> token) {    	
    	return ServerResponse.ok()
    					.contentType(MediaType.APPLICATION_JSON_UTF8)
    					.body(token, TokenResponse.class);
    }
	
	
	
}
