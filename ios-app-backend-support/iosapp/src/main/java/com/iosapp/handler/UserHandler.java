package com.iosapp.handler;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.model.User;
import com.iosapp.payload.InformativeResponse;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	private final UserService userService;
	
	public UserHandler(UserService userService) {
		this.userService = userService;
	}

	public Mono<ServerResponse> subscribe(ServerRequest request) {	
		return defaultReadResponse(
				this.userService.subscribe(), Mono.just(new InformativeResponse("You subscribed.")));
	}
	
	public Mono<ServerResponse> unsubscribe(ServerRequest request) {	
		return defaultReadResponse(
				this.userService.subscribe(), Mono.just(new InformativeResponse("You unsubscribed.")));

	}
	
    private static Mono<ServerResponse> defaultReadResponse(Publisher<User> users,
    		Publisher<InformativeResponse> message) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)          
            .body(message, InformativeResponse.class);
    }
}
