package de.wps.bikehh.passwortzuruecksetzung.service;

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

import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.framework.api.exception.ApiRequestException;
import de.wps.bikehh.framework.api.exception.ErrorCode;
import de.wps.bikehh.passwortzuruecksetzung.material.Reset;
import de.wps.bikehh.passwortzuruecksetzung.repository.PasswordResetRepository;

@Service
public class PasswordResetService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private PasswordResetRepository passwordResetRepository;
	private UserRepository userRepository;
	private SmtpService smtpService;
	private TokenService tokenService;

	@Autowired
	public PasswordResetService(TokenService tokenService,
			PasswordResetRepository passwordAuthenticationRepository,
			UserRepository userAuthenticationRepository, SmtpService smtpService) {
		this.passwordResetRepository = passwordAuthenticationRepository;
		this.userRepository = userAuthenticationRepository;
		this.smtpService = smtpService;
		this.tokenService = tokenService;
	}

	/**
	 * verschickt eine passwort-reset mail
	 *
	 *
	 * @param email
	 *            email des Users
	 */
	public void requestResetMail(String email) {
		if (!userRepository.existsByEmailAddress(email)) {
			return;
		}

		User user = userRepository.findByEmailAddress(email);

		// Delete in case token for user already exists
		Reset reset = passwordResetRepository.findByUserId(user.getId()).orElse(null);
		if (reset != null) {
			passwordResetRepository.delete(reset);
		}

		String token = tokenService.generateSecureToken();
		Reset resetToken = new Reset();
		resetToken.setToken(token);
		resetToken.setUser(user);

		passwordResetRepository.save(resetToken);

		// Send mail
		Mail mail = new Mail(user.getEmailAddress(), "Reset password");

		// TODO set frontend link. Add config file with host depending on
		// environment
		String redirectLink = String.format("http://localhost:8080/api/password?token=%s", token);

		Map<String, Object> model = new HashMap<>();
		// model.put("username", user.getEmailAddress());
		model.put("link", redirectLink);
		mail.setModel(model);

		smtpService.sendMail(mail, SmtpService.Templates.RESET);

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
		Reset reset = passwordResetRepository.findByToken(token).orElse(null);
		if (reset == null) {
			throw new ApiRequestException(ErrorCode.invalid_token, HttpStatus.NOT_FOUND);
		}

		User user = reset.getUser();
		String encodedPassword = passwordEncoder.encode(password);
		user.setEncryptedPassword(encodedPassword);

		// Delete reset token and set new password
		passwordResetRepository.delete(reset);
		userRepository.save(user);
	}

	/**
	 * löscht einen reset-token
	 *
	 * @param userId
	 *            id des Users
	 */
	public void deleteResetToken(Long userId) {
		Reset reset = passwordResetRepository.findByUserId(userId).orElse(null);
		if (reset != null) {
			passwordResetRepository.delete(reset);
		}
	}

	/**
	 * scheduler, welcher abgelaufene password-reset tokens löscht
	 */
	@Scheduled(fixedRate = 10000)
	public void deleteExpiredTokens() {
		List<Reset> list = new ArrayList<>();
		passwordResetRepository.findAll().forEach(list::add);

		Date now = new Date();
		long oneHour = 1000 * 60 * 60;

		for (Reset r : list) {
			if ((now.getTime() - r.getCreatedAt().getTime()) > oneHour) {
				passwordResetRepository.delete(r);
			}
		}
	}

}
