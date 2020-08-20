package de.wps.bikehh.benutzerverwaltung;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.wps.bikehh.authentifizierung.service.AuthenticationService;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.passwortzuruecksetzung.service.PasswordResetService;
import de.wps.bikehh.verifizierung.service.VerificationService;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceTest {

	@Mock
	AuthenticationService authService;
	@Mock
	VerificationService verifyDetailService;
	@Mock
	PasswordResetService passwordDetailService;

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserService userDetailService;

	@Test
	@Ignore
	public void testCreateUser() {
		userDetailService.createUser("test@web.de", "Test1234", Rollen.ROLE_USER);
		Mockito.verify(userRepository).existsByEmailAddress(anyString());
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void testUpdateUser() {

		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		User user = new User();
		userDetailService.updateUser(user, "new@test.de", 2);
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setId(1L);

		userDetailService.deleteUser(user);

		Mockito.verify(userRepository).delete(Mockito.any(User.class));
	}

	@Test
	@Ignore
	public void testUpdatePassword() {

		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		User user = new User();
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setEncryptedPassword(encoder.encode("Test1234"));
		userDetailService.updatePassword(user, "Test1234", "Kern1234");
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

}
