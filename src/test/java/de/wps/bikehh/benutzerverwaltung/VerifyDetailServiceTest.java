package de.wps.bikehh.benutzerverwaltung;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.material.Verification;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.VerificationAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailService;
import de.wps.bikehh.framework.api.exception.ApiRequestException;

@RunWith(MockitoJUnitRunner.class)
public class VerifyDetailServiceTest {

	@Mock
	VerificationAuthenticationRepository _verificationRepository;
	@Mock
	UserAuthenticationRepository _userAuthenticationRepository;
	@Mock
	SmtpService _smtpService;

	@InjectMocks
	VerifyDetailService _verifyDetailService;

	@Test
	@Ignore("TokenService mitgeben")
	public void testVerificationMail() {
		String testEmail = "test@mail.com";
		User testUser = new User(testEmail, "testPw123");
		testUser.setId(1L);

		// Default case
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(true);
		when(_userAuthenticationRepository.findByEmailAddress(testEmail)).thenReturn(testUser);
		when(_verificationRepository.save(any(Verification.class))).thenReturn(new Verification());

		_verifyDetailService.requestVerificationMail(testEmail);

		verify(_verificationRepository).save(any(Verification.class));
		verify(_smtpService).sendMail(any(Mail.class), any(SmtpService.Templates.class));

		// when user doesnt exists
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(false);
		_verifyDetailService.requestVerificationMail(testEmail);

		// with existing verification token
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(true);
		when(_verificationRepository.findByUserId(anyLong())).thenReturn(java.util.Optional.of(new Verification()));

		_verifyDetailService.requestVerificationMail(testEmail);

		verify(_verificationRepository).delete(any(Verification.class));
		verify(_verificationRepository, times(2)).save(any(Verification.class));
	}

	@Test(expected = ApiRequestException.class)
	public void testVerifyUser() {
		String testEmail = "test@mail.com";
		User testUser = new User(testEmail, "testPw123");
		testUser.setId(1L);

		String verifyToken = "abcdefghijklmnopqrstuvwxyz";
		Verification verification = new Verification(testUser.getId(), verifyToken);

		// when token doesnt exists
		_verifyDetailService.verifyUser(verifyToken);

		// when user doesnt exists
		when(_verificationRepository.findByToken(verifyToken)).thenReturn(java.util.Optional.of(verification));
		_verifyDetailService.verifyUser(verifyToken);

		// Default case
		when(_userAuthenticationRepository.findById(testUser.getId())).thenReturn(java.util.Optional.of(testUser));

		_verifyDetailService.verifyUser(verifyToken);
		verify(_verificationRepository).delete(any(Verification.class));
		verify(_userAuthenticationRepository).save(any(User.class));
	}

	@Test
	public void testDeleteVerification() {
		when(_verificationRepository.findByUserId(anyLong())).thenReturn(Optional.of(new Verification()));

		_verifyDetailService.deleteVerification(1L);
		verify(_verificationRepository).delete(any(Verification.class));
	}
}
