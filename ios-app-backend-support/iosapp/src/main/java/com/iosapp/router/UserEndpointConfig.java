package com.iosapp.router;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iosapp.handler.UserHandler;

@Configuration
public class UserEndpointConfig {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserHandler handler) { 
        	return route(RequestPredicates.POST("/subscribe"), handler::subscribe)
        			.andRoute(RequestPredicates.POST("/unsubscribe"), handler::unsubscribe);
    }
}
