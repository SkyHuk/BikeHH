package de.wps.bikehh.benutzerverwaltung;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.wps.bikehh.benutzerverwaltung.material.Mail;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;
import de.wps.bikehh.benutzerverwaltung.service.SmtpService;
import de.wps.bikehh.passwortzuruecksetzung.material.Reset;
import de.wps.bikehh.passwortzuruecksetzung.repository.PasswordResetRepository;
import de.wps.bikehh.passwortzuruecksetzung.service.PasswordResetService;

@RunWith(MockitoJUnitRunner.class)
public class PasswordDetailServiceTest {
	@Mock
	PasswordResetRepository _passwordAuthenticationRepository;
	@Mock
	UserRepository _userAuthenticationRepository;
	@Mock
	SmtpService _smtpService;

	@InjectMocks
	PasswordResetService _passwordDetailService;

	@Test
	@Ignore("TokenService mitgeben")
	public void testRequestResetMail() {
		String testEmail = "test@mail.com";
		User testUser = new User(testEmail, "testPw123", Rollen.ROLE_USER);
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
		when(_passwordAuthenticationRepository.findByUserId(anyLong())).thenReturn(new Reset());

		_passwordDetailService.requestResetMail(testEmail);

		verify(_passwordAuthenticationRepository).delete(any(Reset.class));
		verify(_passwordAuthenticationRepository, times(2)).save(any(Reset.class));
	}

}
