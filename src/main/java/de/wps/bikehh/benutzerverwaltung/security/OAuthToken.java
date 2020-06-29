package de.wps.bikehh.benutzerverwaltung.security;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OAuthToken extends UsernamePasswordAuthenticationToken {

    private Session session;

    public OAuthToken(String token) {
        super(null, token);
    }

    public OAuthToken(User user, String token) {
        super(user, token);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
