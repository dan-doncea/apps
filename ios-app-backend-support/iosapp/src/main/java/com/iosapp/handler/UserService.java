package com.iosapp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iosapp.model.User;
import com.iosapp.utilities.DefaultUserBuilderService;

import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	@Autowired
	private final DefaultUserBuilderService usrBuilderService;
	
	public UserService(DefaultUserBuilderService usrBuilderService) {
		this.usrBuilderService = usrBuilderService;
	}

	public Mono<User> subscribe() {
		return usrBuilderService.findByUsername(
				SecurityContextHolder.getContext().getAuthentication().getName())
				.doOnSuccess(user -> user.setSubscribed(true));
	}
	
	public Mono<User> unsubscribe() {
		return usrBuilderService.findByUsername(
				SecurityContextHolder.getContext().getAuthentication().getName())
				.doOnSuccess(user -> user.setSubscribed(false));
	}
}
