package com.neyugniat.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeHttpRequests(authorize -> 
				authorize
					.requestMatchers("/api/**").authenticated()
					.anyRequest().permitAll()
			)
			.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
			.csrf().disable()
			.cors().configurationSource(corsConfigurationSource()) // Externalize the CORS configuration
			.and()
			.httpBasic().disable()  // Disable httpBasic if not required
			.formLogin().disable(); // Disable formLogin as JWT-based auth is used

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration cfg = new CorsConfiguration();
			cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
			cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			cfg.setAllowCredentials(true);
			cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
			cfg.setExposedHeaders(Collections.singletonList("Authorization"));
			cfg.setMaxAge(3600L);
			return cfg;
		};
	}
}
