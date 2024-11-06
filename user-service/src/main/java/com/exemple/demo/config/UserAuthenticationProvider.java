package com.exemple.demo.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exemple.demo.exception.Adminservice;
import com.exemple.demo.exception.UserDto;
import com.exemple.demo.exception.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserAuthenticationProvider {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	private final Adminservice userService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String email) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000); // 1 hour
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		log.info("inside create Token");
		return JWT.create().withIssuer(email).withIssuedAt(now).withExpiresAt(validity).sign(algorithm);
	}

	public Authentication validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);


		JWTVerifier verifier = JWT.require(algorithm).build();



		DecodedJWT decoded = verifier.verify(token);


		UserDto user = userService.findByLogin(decoded.getIssuer());


		return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
	}




}