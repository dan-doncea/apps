package com.iosapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	@Autowired
	private CustomAuthenticationEntryPoint authEntryPoint;
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
	    return http
	      .csrf().disable()
		  .formLogin().disable()
		  .httpBasic().disable()
		  .authenticationManager(authenticationManager)
		  .securityContextRepository(securityContextRepository)
	      .authorizeExchange()
	      .pathMatchers(HttpMethod.OPTIONS).permitAll()
          .pathMatchers("/login").permitAll()
          .pathMatchers("/**").permitAll()
	      .anyExchange().authenticated()
	      .and().exceptionHandling().authenticationEntryPoint(authEntryPoint)
	      .and().build();
	}
}
