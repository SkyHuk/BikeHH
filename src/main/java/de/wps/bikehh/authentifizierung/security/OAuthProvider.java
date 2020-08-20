package de.wps.bikehh.authentifizierung.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.authentifizierung.service.AuthenticationService;
import de.wps.bikehh.framework.api.exception.ErrorCode;

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
		Session session = authService.getSessionByToken(token);
		if (session == null) {
			throw new BadCredentialsException(ErrorCode.invalid_token);
		}

		OAuthToken nAuth = new OAuthToken(session.getUser(), token);
		nAuth.setSession(session);
		return nAuth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return OAuthToken.class.isAssignableFrom(authentication);
	}

}