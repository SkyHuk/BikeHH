package de.wps.bikehh.verifizierung.service;

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
import de.wps.bikehh.verifizierung.material.Verification;
import de.wps.bikehh.verifizierung.repository.VerificationRepository;

@Service
public class VerificationService {

	private VerificationRepository verificationRepository;

	private UserService userService;
	private TokenService tokenService;
	private SmtpService smtpService;

	@Autowired
	public VerificationService(
			VerificationRepository verificationRepository,
			UserService userService,
			TokenService tokenService,
			SmtpService smtpService) {
		this.verificationRepository = verificationRepository;
		this.userService = userService;
		this.tokenService = tokenService;
		this.smtpService = smtpService;
	}

	public void requestVerificationMail(String email) throws Exception {
		Contract.notEmpty(email, "email");
		Contract.check(userService.existsByEmail(email), "userService.existsByEmail(email)");

		User user = userService.getUserByEmail(email);
		if (user.getIsVerified()) {
			throw new Exception("Der Benutzer ist bereits verifiziert.");
		}

		// Delete in case token for user already exists
		if (verificationRepository.existsByUser(user)) {
			verificationRepository.deleteByUser(user);
		}

		String token = tokenService.generateSecureToken();
		Verification verification = new Verification();
		verification.setToken(token);
		verification.setUser(user);

		verificationRepository.save(verification);

		// TODO set frontend link to password reset form
		String redirectLink = String.format("http://localhost:8080/api/verify?token=%s", token);

		Mail mail = new Mail(user.getEmailAddress(), "Verify Account");
		Map<String, Object> model = new HashMap<>();
		model.put("link", redirectLink);
		mail.setModel(model);

		smtpService.sendMail(mail, SmtpService.Templates.VERIFY);
	}

	public User verifyUser(String token) {
		Contract.notEmpty(token, "token");
		Contract.check(verificationRepository.existsByToken(token), "verificationRepository.existsByToken(token)");

		Verification verification = verificationRepository.findByToken(token);
		User updatedUser = userService.verifyUser(verification.getUser());

		verificationRepository.delete(verification);
		return updatedUser;
	}

	/**
	 * scheduler, welcher abgelaufene verify tokens löscht
	 */
	@Scheduled(fixedRate = 10000)
	public void deleteExpiredTokens() {
		List<Verification> list = new ArrayList<>();
		verificationRepository.findAll().forEach(list::add);

		Date now = new Date();
		long oneHour = 1000 * 60 * 60;

		for (Verification v : list) {
			if ((now.getTime() - v.getCreatedAt().getTime()) > oneHour) {
				verificationRepository.delete(v);
			}
		}
	}
}
