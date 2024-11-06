package com.exemple.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class UserInactiveFilter extends OncePerRequestFilter {

	private final UserAuthenticationProvider userAuthenticationProvider;

	private final List<String> allowedPaths = Arrays.asList("/swagger-ui/", "/favicon.ico", "/v3/api-docs",
			"/admin/login", "/admin/saveDetailsOfUsers");

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		String requestURI = httpServletRequest.getRequestURI();
		if (!isAllowedPath(requestURI)) {
			int statusCode = 0;
			String errorMessage = "";
			String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			if (header != null) {
				String[] authElements = header.split(" ");


			} else {
				statusCode = 402;
				errorMessage = "{\"error\": \"Token Missing\"}";
				handleInactiveUser(httpServletResponse, statusCode, errorMessage);
				return;
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private boolean isAllowedPath(String requestURI) {
		return allowedPaths.stream().anyMatch(requestURI::startsWith);
	}

	private void handleInactiveUser(HttpServletResponse response, int statusCode, String errorMessage)
			throws IOException {
		// Handle user inactivity
		response.setStatus(statusCode); // or any other appropriate status code
		response.setContentType("application/json");
		response.getWriter().write(errorMessage); // Customize the response message if needed
	}
}
