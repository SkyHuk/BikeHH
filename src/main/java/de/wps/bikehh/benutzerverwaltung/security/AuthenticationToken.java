package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.material.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    public AuthenticationToken(String token) {
        super(null, token);
    }

    public AuthenticationToken(User user, String token) {
        super(user, token);
    }
}
