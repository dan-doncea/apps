package com.iosapp.router;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.handler.ArticleHandler;

@Configuration
public class ArticleEndpointConfiguration {
	
	@Bean
	RouterFunction<ServerResponse> routes(ArticleHandler handler) {
		return route(RequestPredicates.POST("/article"), handler::create)
			.andRoute(RequestPredicates.PUT("/article/{id}"), handler::update)
			.andRoute(RequestPredicates.GET("/article/{id}"), handler::getById)
			.andRoute(RequestPredicates.GET("/articles"), handler::getAll)
			.andRoute(RequestPredicates.DELETE("/article/{id}"), handler::deleteById);
	}
}
