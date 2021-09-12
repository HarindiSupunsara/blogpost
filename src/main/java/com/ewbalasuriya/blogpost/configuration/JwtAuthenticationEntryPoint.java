package com.ewbalasuriya.blogpost.configuration;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Viraj.Kulathunga
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 2292097436961357827L;

	@Value("${error.auth.string}")
	private String error;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse,
			AuthenticationException authException) throws IOException {
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpServletResponse.setContentType("application/json");
		httpServletResponse.getWriter().write(error);
		httpServletResponse.getWriter().flush();
		httpServletResponse.getWriter().close();
//		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
