package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    private AuthService authService;

    @Autowired
    public AuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationToken auth = (AuthenticationToken) authentication;
        String token = (String) auth.getCredentials();
        User user = authService.getUserByToken(token);
        if (user == null) {
            throw new BadCredentialsException("Unauthorized");
        }

        AuthenticationToken nAuth = new AuthenticationToken(user, token);
        return nAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
