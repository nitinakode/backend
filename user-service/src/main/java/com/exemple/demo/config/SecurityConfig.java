package com.exemple.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

	private final UserAuthenticationProvider userAuthenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
				.addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
				.addFilterBefore(new UserInactiveFilter(userAuthenticationProvider),
						UsernamePasswordAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(requests -> requests.requestMatchers("/swagger-ui/**", "/favicon.ico",

						"/v3/api-docs/**", "/admin/login", "/admin/saveDetailsOfUsers").permitAll().anyRequest().authenticated());
		return http.build();
	}
}