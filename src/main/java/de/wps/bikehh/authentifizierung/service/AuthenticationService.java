package de.wps.bikehh.authentifizierung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.authentifizierung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.framework.Contract;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private SessionRepository sessionRepository;

	private UserService userService;
	private TokenService tokenService;

	@Autowired
	public AuthenticationService(
			SessionRepository sessionRepository,
			UserService userService,
			TokenService tokenService) {
		this.sessionRepository = sessionRepository;
		this.tokenService = tokenService;
		this.userService = userService;
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
		sessionRepository.save(session);

		return session;
	}

	public void logoutUser(Session session) {
		Contract.notNull(session, "session");

		sessionRepository.delete(session);
	}

	public Session getSessionByToken(String token) {
		Contract.notEmpty(token, "token");

		return sessionRepository.findByToken(token).orElse(null);
	}
}
