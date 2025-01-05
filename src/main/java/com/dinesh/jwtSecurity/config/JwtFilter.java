package com.dinesh.jwtSecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dinesh.jwtSecurity.domain.MyUserDetail;
import com.dinesh.jwtSecurity.service.impl.JwtService;
import com.dinesh.jwtSecurity.service.impl.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
    ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		String token=null;
		String username=null;
		// Retrieve the Authorization header
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}
		// If the token is valid and no authentication is set in the context
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=context.getBean(UserDetailServiceImpl.class).loadUserByUsername(username);
			
			// Validate token and set authentication
			if(jwtService.validateToken(token,userDetails)) {
				   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource()
	                        .buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		// Continue the filter chain
		filterChain.doFilter(request, response);
	}

}
