package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.PasswordAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.util.Utils;

@Service
public class PasswordDetailService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private PasswordAuthenticationRepository _passwordAuthenticationRepository;
	private UserAuthenticationRepository _userAuthenticationRepository;
	private SmtpService _smtpService;

	@Autowired
	public PasswordDetailService(PasswordAuthenticationRepository passwordAuthenticationRepository,
			UserAuthenticationRepository userAuthenticationRepository, SmtpService smtpService) {
		this._passwordAuthenticationRepository = passwordAuthenticationRepository;
		this._userAuthenticationRepository = userAuthenticationRepository;
		this._smtpService = smtpService;
	}

	/**
	 * verschickt eine passwort-reset mail
	 *
	 *
	 * @param email
	 *            email des Users
	 */
	public void requestResetMail(String email) throws ApiRequestException {
		if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
			return;
		}

		User user = _userAuthenticationRepository.findByEmailAddress(email);

		// Delete in case token for user already exists
		Reset reset = _passwordAuthenticationRepository.findByUserId(user.getId()).orElse(null);
		if (reset != null) {
			_passwordAuthenticationRepository.delete(reset);
		}

		String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
		Reset resetToken = new Reset(user.getId(), token);

		_passwordAuthenticationRepository.save(resetToken);

		// Send mail
		Mail mail = new Mail(user.getEmailAddress(), "Reset password");

		// @TODO set frontend link. Add config file with host depending on
		// environment
		String redirectLink = String.format("http://localhost:8080/api/password?token=%s", token);

		Map<String, String> model = new HashMap<>();
		// model.put("username", user.getEmailAddress());
		model.put("link", redirectLink);
		mail.setModel(model);

		_smtpService.sendMail(mail, SmtpService.Templates.RESET);

	}

	/**
	 * setzt ein neues Passwort
	 *
	 * @param token
	 *            token, welcher den User identifiziert
	 * @param password
	 *            passwort des Users
	 */
	public void resetPassword(String password, String token) throws ApiRequestException {
		Reset reset = _passwordAuthenticationRepository.findByToken(token).orElse(null);
		if (reset == null) {
			throw new ApiRequestException(ErrorCode.invalid_token, HttpStatus.NOT_FOUND);
		}

		User user = _userAuthenticationRepository.findById(reset.getUserId()).orElse(null);
		if (user == null) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		String encodedPassword = passwordEncoder.encode(password);
		user.setEncryptedPassword(encodedPassword);

		// Delete reset token and set new password
		_passwordAuthenticationRepository.delete(reset);
		_userAuthenticationRepository.save(user);
	}

	/**
	 * löscht einen reset-token
	 *
	 * @param userId
	 *            id des Users
	 */
	public void deleteResetToken(Long userId) {
		Reset reset = _passwordAuthenticationRepository.findByUserId(userId).orElse(null);
		if (reset != null) {
			_passwordAuthenticationRepository.delete(reset);
		}
	}

	/**
	 * scheduler, welcher abgelaufene password-reset tokens löscht
	 */
	@Scheduled(fixedRate = 10000)
	public void deleteExpiredTokens() {
		List<Reset> list = new ArrayList<>();
		_passwordAuthenticationRepository.findAll().forEach(list::add);

		Date now = new Date();
		long oneHour = 1000 * 60 * 60;

		for (Reset r : list) {
			if ((now.getTime() - r.getCreatedAt().getTime()) > oneHour) {
				_passwordAuthenticationRepository.delete(r);
			}
		}
	}

}
