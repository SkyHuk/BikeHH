package de.wps.bikehh.authentifizierung.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import de.wps.bikehh.framework.api.exception.ApiException;

public class OAuthEntryPoint implements AuthenticationEntryPoint {

	/**
	 * wird aufgerufen, falls die Authentication fehlschlägt
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ApiException e = new ApiException(HttpStatus.UNAUTHORIZED);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(e.toString());
	}
}
