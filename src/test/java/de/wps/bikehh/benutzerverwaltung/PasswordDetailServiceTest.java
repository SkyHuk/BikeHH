package de.wps.bikehh.benutzerverwaltung;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.PasswordAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailService;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;

@RunWith(MockitoJUnitRunner.class)
public class PasswordDetailServiceTest {
	@Mock
	PasswordAuthenticationRepository _passwordAuthenticationRepository;
	@Mock
	UserAuthenticationRepository _userAuthenticationRepository;
	@Mock
	SmtpService _smtpService;

	@InjectMocks
	PasswordDetailService _passwordDetailService;

	@Test
	@Ignore("TokenService mitgeben")
	public void testRequestResetMail() {
		String testEmail = "test@mail.com";
		User testUser = new User(testEmail, "testPw123");
		testUser.setId(1L);

		// Default case
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(true);
		when(_userAuthenticationRepository.findByEmailAddress(testEmail)).thenReturn(testUser);
		when(_passwordAuthenticationRepository.save(any(Reset.class))).thenReturn(new Reset());

		_passwordDetailService.requestResetMail(testEmail);
		verify(_passwordAuthenticationRepository).save(any(Reset.class));
		verify(_smtpService).sendMail(any(Mail.class), any(SmtpService.Templates.class));

		// when user doesnt exists
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(false);
		_passwordDetailService.requestResetMail(testEmail);

		// with existing verification token
		when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(true);
		when(_passwordAuthenticationRepository.findByUserId(anyLong())).thenReturn(java.util.Optional.of(new Reset()));

		_passwordDetailService.requestResetMail(testEmail);

		verify(_passwordAuthenticationRepository).delete(any(Reset.class));
		verify(_passwordAuthenticationRepository, times(2)).save(any(Reset.class));
	}

	@Test(expected = ApiRequestException.class)
	public void testResetPassword() {
		String testEmail = "test@mail.com";
		String testPassword = "TestPw123";
		User testUser = new User(testEmail, testPassword);
		testUser.setId(1L);

		String resetToken = "abcdefghijklmnopqrstuvwxyz";
		Reset reset = new Reset(testUser.getId(), resetToken);

		// when token doesnt exists
		_passwordDetailService.resetPassword(testPassword, resetToken);

		// when user doesnt exists
		when(_passwordAuthenticationRepository.findByToken(anyString())).thenReturn(java.util.Optional.of(reset));
		_passwordDetailService.resetPassword(testPassword, resetToken);

		// Default case
		when(_userAuthenticationRepository.findById(anyLong())).thenReturn(java.util.Optional.of(testUser));

		_passwordDetailService.resetPassword(testPassword, resetToken);
		verify(_passwordAuthenticationRepository).delete(any(Reset.class));
		verify(_userAuthenticationRepository).save(any(User.class));
	}

	@Test
	public void testDeleteResetToken() {
		when(_passwordAuthenticationRepository.findByUserId(anyLong())).thenReturn(Optional.of(new Reset()));

		_passwordDetailService.deleteResetToken(1L);
		verify(_passwordAuthenticationRepository).delete(any(Reset.class));
	}
}
