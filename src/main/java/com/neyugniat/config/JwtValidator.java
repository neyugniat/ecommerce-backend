package com.neyugniat.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
	
		if (jwt != null && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7);
			
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

				JwtParser jwtParser = Jwts.parser().setSigningKey(key).build();

				Claims claims = jwtParser.parseClaimsJws(jwt).getBody();

				String email = String.valueOf(claims.get("email"));

				String authorities = String.valueOf(claims.get("authorities"));

				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				System.out.println("Invalid token: " + jwt); // Log the token
				e.printStackTrace(); // Log the stack trace
				throw new BadCredentialsException("Invalid token...", e);
			}
		} else {
			System.out.println("No JWT token found in header.");
		}

		filterChain.doFilter(request, response);
	}
}
