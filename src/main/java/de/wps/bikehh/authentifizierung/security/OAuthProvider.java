package de.wps.bikehh.authentifizierung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.authentifizierung.service.AuthenticationService;

@Component
public class OAuthProvider implements AuthenticationProvider {

	private AuthenticationService authService;

	@Autowired
	public OAuthProvider(AuthenticationService authService) {
		this.authService = authService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OAuthToken auth = (OAuthToken) authentication;
		String token = (String) auth.getCredentials();

		if (!authService.hasSession(token)) {
			throw new BadCredentialsException("Invalid Token");
		}

		Session session = authService.getSessionByToken(token);
		OAuthToken nAuth = new OAuthToken(session);
		return nAuth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuthToken.class.isAssignableFrom(authentication);
	}

}
