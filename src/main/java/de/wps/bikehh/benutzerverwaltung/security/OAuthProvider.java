package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.exception.ApiException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        Session session = authService.getSessionByToken(token);
        if (session == null) {
            throw new BadCredentialsException(ErrorCode.unauthorized);
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
