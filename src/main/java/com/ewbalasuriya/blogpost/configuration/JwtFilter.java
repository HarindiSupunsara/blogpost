package com.ewbalasuriya.blogpost.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ewbalasuriya.blogpost.service.UserService;
import com.ewbalasuriya.blogpost.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil
	jwtUtil;

	@Autowired
	private UserService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		Map<String, String> map = null;
		String authorizationHeader = httpServletRequest.getHeader("authorization");
		String token = null;
		String userName = null;

		try {
			if (authorizationHeader != null && !authorizationHeader.trim().equals("")) {
				token = authorizationHeader;
				userName = jwtUtil.extractUsername(token);
			}
			
			System.out.println(userName);

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = authenticationService.loadUserByUsername(userName);

				if (jwtUtil.validateToken(token, userDetails)) {
					map = new HashMap<String, String>();
					map.put("bearer-access-token", authorizationHeader);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(map);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} else {
					throw new AuthenticationCredentialsNotFoundException("Validation Faild");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			throw new AuthenticationCredentialsNotFoundException("Validation Faild");
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
