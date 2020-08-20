package de.wps.bikehh.verifizierung.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;
import de.wps.bikehh.benutzerverwaltung.service.TokenService;
import de.wps.bikehh.framework.api.exception.ApiRequestException;
import de.wps.bikehh.framework.api.exception.ErrorCode;
import de.wps.bikehh.verifizierung.material.Verification;
import de.wps.bikehh.verifizierung.repository.VerificationRepository;

@Service
public class VerificationService {

	private VerificationRepository verificationRepository;

	// FIXME: Keine direkten Calls auf einem fremden Repository!
	private UserRepository _userAuthenticationRepository;
	private SmtpService smtpService;
	private TokenService tokenService;

	@Autowired
	public VerificationService(TokenService tokenService,
			VerificationRepository verificationRepository,
			UserRepository userAuthenticationRepository, SmtpService smtpService) {
		this.verificationRepository = verificationRepository;
		this._userAuthenticationRepository = userAuthenticationRepository;
		this.smtpService = smtpService;
		this.tokenService = tokenService;
	}

	/**
	 * verschickt eine account-verifizieren Mail raus
	 *
	 * @param email
	 *            email
	 */
	public void requestVerificationMail(String email) throws ApiRequestException {
		if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
			return;
		}

		User user = _userAuthenticationRepository.findByEmailAddress(email);
		if (user.getIsVerified()) {
			return;
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

		// Send mail
		Mail mail = new Mail(user.getEmailAddress(), "Verify Account");

		// TODO set frontend link. Add config file with host depending on
		// environment
		String redirectLink = String.format("http://localhost:8080/api/verify?token=%s", token);

		Map<String, Object> model = new HashMap<>();
		// model.put("username", user.getUsername());
		model.put("link", redirectLink);
		mail.setModel(model);

		smtpService.sendMail(mail, SmtpService.Templates.VERIFY);

	}

	/**
	 * verifiziert den Account eines Users
	 *
	 * @param token
	 *            token, der in der db hinterlegt worden ist
	 */
	public void verifyUser(String token) throws ApiRequestException {
		Verification verification = verificationRepository.findByToken(token).orElse(null);
		if (verification == null) {
			throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
		}

		User user = verification.getUser();
		user.setVerified(true);

		// Delete verification token
		verificationRepository.delete(verification);
		_userAuthenticationRepository.save(user);
	}

	/**
	 * löscht einen verify-token anhand des Users
	 *
	 * @param id
	 *            id des Users
	 */
	public void deleteVerification(User user) {
		if (verificationRepository.existsByUser(user)) {
			verificationRepository.deleteByUser(user);
		}
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
