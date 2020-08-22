package de.wps.bikehh.authentifizierung.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.framework.Contract;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * <Token, Session>
	 */
	private Map<String, Session> sessions;

	private UserService userService;
	private TokenService tokenService;

	@Autowired
	public AuthenticationService(
			UserService userService,
			TokenService tokenService) {
		this.tokenService = tokenService;
		this.userService = userService;
		this.sessions = new HashMap<>();
	}

	public Session loginUser(String email, String password)
			throws UsernameNotFoundException, LockedException, BadCredentialsException {
		Contract.notEmpty(email, "email");
		Contract.notEmpty(password, "password");

		if (!userService.existsByEmail(email)) {
			throw new UsernameNotFoundException("Unbekannter User: " + email);
		}

		User user = userService.getUserByEmail(email);

		if (user.getIsLocked()) {
			throw new LockedException("Nutzer ist gesperrt");
		}

		if (!passwordEncoder.matches(password, user.getEncryptedPassword())) {
			throw new BadCredentialsException("Falsches Passwort");
		}

		Session session = new Session(tokenService.generateSecureToken(), user);
		sessions.put(session.getToken(), session);

		return session;
	}

	public void logoutUser(Session session) {
		Contract.notNull(session, "session");

		sessions.remove(session.getToken());
	}

	public boolean hasSession(String token) {
		Contract.notEmpty(token, "token");

		return sessions.containsKey(token);
	}

	public Session getSessionByToken(String token) {
		Contract.notEmpty(token, "token");
		Contract.check(sessions.containsKey(token), "sessions.containsKey(token)");

		return sessions.get(token);
	}
}
