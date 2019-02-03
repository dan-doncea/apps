package com.iosapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.handler.AuthHandler;

@Configuration
public class AuthEndpointConfig {

    @Bean
    RouterFunction<ServerResponse> authentificationRoutes(AuthHandler handler) { 
        return RouterFunctions
        		.route(RequestPredicates.POST("/login")
        		.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::login);
    }
}
