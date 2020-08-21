package de.wps.bikehh.authentifizierung.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import de.wps.bikehh.framework.api.exception.ApiException;

public class OAuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = request.getHeader("Authorization");

		if (accessToken == null || accessToken.isEmpty()) {
			sendUnauthorizedResponse(response);
			return;
		}

		Authentication auth = new OAuthToken(accessToken);

		// Den Benutzer manuell bei Spring Security anmelden
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);
	}

	private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
		ApiException exception = new ApiException(HttpStatus.UNAUTHORIZED);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(exception.toString());
	}
}
