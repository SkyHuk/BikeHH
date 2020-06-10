package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.material.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OAuthToken extends UsernamePasswordAuthenticationToken {

    public OAuthToken(String token) {
        super(null, token);
    }

    public OAuthToken(User user, String token) {
        super(user, token);
    }
}
