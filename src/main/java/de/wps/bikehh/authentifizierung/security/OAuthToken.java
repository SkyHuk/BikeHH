package de.wps.bikehh.authentifizierung.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;

public class OAuthToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = -8641328994421342814L;

	private Session session;

	public OAuthToken(String token) {
		super(null, token);
	}

	public OAuthToken(User user, String token) {
		super(user, token);
	}

	public OAuthToken(Session session) {
		super(session.getUser(), session.getToken());
		this.session = session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}

}
