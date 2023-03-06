package com.myproject.springbootblogrestapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;



public class JwtAuthenticationFilter extends OncePerRequestFilter {
	//Inject required dependencies
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//get JWT token from http request
		String token = getJWTfromToken(request);
		//validate token
		if(StringUtils.hasText(token)&& jwtTokenProvider.validateToken(token)) {
			//get username from token
			String username = jwtTokenProvider.getUsernameFromJWT(token);
			//load user associated with token
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//set this information to spring security
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	//Bearer <access token>
			private String getJWTfromToken(HttpServletRequest request) {
				String bearerToken = request.getHeader("Authorization");
				if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
					return bearerToken.substring(7,bearerToken.length());
				}
				return null;
			}
			
			

}
