package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.material.Verification;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.VerificationAuthenticationRepository;

@Service
public class VerifyDetailService {

	private VerificationAuthenticationRepository _verificationRepository;
	private UserAuthenticationRepository _userAuthenticationRepository;
	private SmtpService _smtpService;
	private TokenService tokenService;

	@Autowired
	public VerifyDetailService(TokenService tokenService,
			VerificationAuthenticationRepository verificationRepository,
			UserAuthenticationRepository userAuthenticationRepository, SmtpService smtpService) {
		this._verificationRepository = verificationRepository;
		this._userAuthenticationRepository = userAuthenticationRepository;
		this._smtpService = smtpService;
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
		Verification verification = _verificationRepository.findByUserId(user.getId()).orElse(null);
		if (verification != null) {
			_verificationRepository.delete(verification);
		}

		String token = tokenService.generateSecureToken();
		Verification verificationToken = new Verification(user.getId(), token);

		_verificationRepository.save(verificationToken);

		// Send mail
		Mail mail = new Mail(user.getEmailAddress(), "Verify Account");

		// TODO set frontend link. Add config file with host depending on
		// environment
		String redirectLink = String.format("http://localhost:8080/api/verify?token=%s", token);

		Map<String, Object> model = new HashMap<>();
		// model.put("username", user.getUsername());
		model.put("link", redirectLink);
		mail.setModel(model);

		_smtpService.sendMail(mail, SmtpService.Templates.VERIFY);

	}

	/**
	 * verifiziert den Account eines Users
	 *
	 * @param token
	 *            token, der in der db hinterlegt worden ist
	 */
	public void verifyUser(String token) throws ApiRequestException {
		Verification verification = _verificationRepository.findByToken(token).orElse(null);
		if (verification == null) {
			throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
		}

		Long userId = verification.getUserId();
		User user = _userAuthenticationRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		user.setVerified(true);

		// Delete reset token and set new password
		_verificationRepository.delete(verification);
		_userAuthenticationRepository.save(user);
	}

	/**
	 * löscht einen verify-token anhand der User-id
	 *
	 * @param id
	 *            id des Users
	 */
	public void deleteVerification(Long userId) {
		Verification verification = _verificationRepository.findByUserId(userId).orElse(null);
		if (verification != null) {
			_verificationRepository.delete(verification);
		}
	}

	/**
	 * scheduler, welcher abgelaufene verify tokens löscht
	 */
	@Scheduled(fixedRate = 10000)
	public void deleteExpiredTokens() {
		List<Verification> list = new ArrayList<>();
		_verificationRepository.findAll().forEach(list::add);

		Date now = new Date();
		long oneHour = 1000 * 60 * 60;

		for (Verification v : list) {
			if ((now.getTime() - v.getCreatedAt().getTime()) > oneHour) {
				_verificationRepository.delete(v);
			}
		}
	}
}
