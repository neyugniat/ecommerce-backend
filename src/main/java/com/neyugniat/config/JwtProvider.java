package com.neyugniat.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth)	{
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", auth.getName())
				.signWith(key).compact();
		
		return jwt;
	}
	
	public String getEmailFromToken(String jwt) {
	    // Ensure the JWT is properly formatted
	    if (jwt == null || jwt.trim().isEmpty() || !jwt.startsWith("Bearer ")) {
	        throw new IllegalArgumentException("Invalid JWT format");
	    }

	    // Remove "Bearer " prefix
	    jwt = jwt.substring(7);

	    try {
	        // Create JwtParser
	        JwtParser jwtParser = Jwts.parser().setSigningKey(key).build();
	        
	        // Parse the JWT and extract claims
	        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
	        
	        // Extract email from claims
	        String email = claims.get("email", String.class);
	        
	        if (email == null || email.trim().isEmpty()) {
	            throw new IllegalArgumentException("Email claim is missing or empty");
	        }
	        
	        return email;
	    } catch (JwtException | IllegalArgumentException e) {
	        // Handle JWT parsing or validation errors
	        throw new RuntimeException("Failed to parse JWT or extract email", e);
	    }
	}

}