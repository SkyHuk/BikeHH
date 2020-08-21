package de.wps.bikehh.passwortzuruecksetzung.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.passwortzuruecksetzung.material.Reset;
import de.wps.bikehh.passwortzuruecksetzung.repository.PasswordResetRepository;

@Service
public class PasswordResetService {

	private PasswordResetRepository passwordResetRepository;

	private UserService userService;
	private TokenService tokenService;
	private SmtpService smtpService;

	@Autowired
	public PasswordResetService(UserService userService,
			TokenService tokenService,
			PasswordResetRepository passwordResetRepository,
			SmtpService smtpService) {
		this.passwordResetRepository = passwordResetRepository;
		this.userService = userService;
		this.smtpService = smtpService;
		this.tokenService = tokenService;
	}

	public void requestResetMail(String email) {
		Contract.check(userService.existsByEmail(email), "userService.existsByEmail(email)");

		User user = userService.getUserByEmail(email);

		// Delete in case token for user already exists
		if (!passwordResetRepository.existsByUserId(user.getId())) {
			passwordResetRepository.deleteByUserId(user.getId());
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

	public User resetPassword(String password, String token) {
		Contract.notEmpty(password, "password");
		Contract.notEmpty(token, "token");
		Contract.check(passwordResetRepository.existsByToken(token), "token exists");

		Reset reset = passwordResetRepository.findByToken(token);
		User updatedUser = userService.setPassword(reset.getUser(), password);

		passwordResetRepository.delete(reset);
		return updatedUser;
	}

	/**
	 * Scheduler, der abgelaufene password-reset tokens löscht
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
