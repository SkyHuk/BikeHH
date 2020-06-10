package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OAuthProvider implements AuthenticationProvider {

    private AuthService authService;

    @Autowired
    public OAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuthToken auth = (OAuthToken) authentication;
        String token = (String) auth.getCredentials();
        User user = authService.getUserByToken(token);
        if (user == null) {
            throw new BadCredentialsException(ErrorCode.unauthorized);
        }

        OAuthToken nAuth = new OAuthToken(user, token);
        return nAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuthToken.class.isAssignableFrom(authentication);
    }
}
