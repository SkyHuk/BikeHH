package de.wps.bikehh.authentifizierung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.framework.api.exception.ApiRequestException;
import de.wps.bikehh.framework.api.exception.ErrorCode;

@Service
public class AuthenticationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private TokenService tokenService;

	private SessionRepository _sessionRepository;
	private UserAuthenticationRepository _userAuthenticationRepository;

	@Autowired
	public AuthenticationService(TokenService tokenService,
			SessionRepository sessionRepository,
			UserAuthenticationRepository _userAuthenticationRepository) {
		this.tokenService = tokenService;
		this._sessionRepository = sessionRepository;
		this._userAuthenticationRepository = _userAuthenticationRepository;
	}

	/**
	 * loggt einen User ein
	 *
	 * erstellt eine Session in der Datenbank
	 *
	 * @param email
	 *            email des Users
	 * @param password
	 *            passwort des Users
	 * @return eine gültige Session
	 */
	public Session loginUser(String email, String password) throws BadCredentialsException {
		if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
			throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
		}

		User user = _userAuthenticationRepository.findByEmailAddress(email);
		if (user.getIsLocked()) {
			throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
		}
		if (!passwordEncoder.matches(password, user.getEncryptedPassword())) {
			throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
		}

		Session session = new Session(tokenService.generateSecureToken(), user);
		_sessionRepository.save(session);

		return session;
	}

	/**
	 * loggt einen User aus
	 *
	 * löscht die Session in der Datenbank
	 *
	 * @param session
	 *            die aktuelle session des User
	 */
	public void logoutUser(Session session) {
		_sessionRepository.delete(session);
	}

	public void logoutAllSession(Long userId) {
		List<Session> sessions = _sessionRepository.findAllByUserId(userId);
		for (Session s : sessions) {
			_sessionRepository.delete(s);
		}
	}

	/**
	 * gibt eine Session anhand ihres Tokens zurück
	 *
	 *
	 * @param token
	 *            session-token
	 * @return Session
	 */
	public Session getSessionByToken(String token) {
		return _sessionRepository.findByToken(token).orElse(null);
	}
}
