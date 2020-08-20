package de.wps.bikehh.benutzerverwaltung;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsDto;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailService;
import de.wps.bikehh.passwortzuruecksetzung.service.PasswordResetService;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceTest {

	@Mock
	AuthenticationService authService;
	@Mock
	VerifyDetailService verifyDetailService;
	@Mock
	PasswordResetService passwordDetailService;

	@Mock
	private UserAuthenticationRepository userRepository;
	@InjectMocks
	private UserDetailService userDetailService;

	// ------------------

	@Test
	@Ignore
	public void testCreateUser() {
		userDetailService.createUser("test@web.de", "Test1234");
		Mockito.verify(userRepository).existsByEmailAddress(anyString());
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void testUpdateUser() {

		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		User user = new User();
		UpdateUserDetailsDto userUpdate = new UpdateUserDetailsDto();
		userUpdate.setEmail("new@test.de");
		userUpdate.setPrivacySetting(2);
		userDetailService.updateUser(user, userUpdate);
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setId(1L);

		userDetailService.deleteUser(user);

		Mockito.verify(userRepository).delete(Mockito.any(User.class));
		Mockito.verify(authService).logoutAllSession(anyLong());
		Mockito.verify(verifyDetailService).deleteVerification(anyLong());
		Mockito.verify(passwordDetailService).deleteResetToken(anyLong());
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

	@Test
	public void testRetrieveUsers() {
		List<User> allUser = Arrays.asList(new User("test@mail.com", "Password123", Rollen.ROLE_USER),
				new User("another@mail.com", "anotherPassword123", Rollen.ROLE_USER),
				new User("test2@mail.com", "Password123", Rollen.ROLE_ADMIN));
		when(userRepository.findAll()).thenReturn(allUser);

		List<User> result = userDetailService.retrieveUsers();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetUserById() {
		User user = new User("test@test.com", "Password1234");
		user.setId(222L);
		when(userRepository.existsById(anyLong())).thenReturn(true);
		userDetailService.getUserById(user.getId());
		Mockito.verify(userRepository).existsById(anyLong());
		Mockito.verify(userRepository).findById(anyLong());
	}

	@Test
	public void testDeleteUserById() {
		User user = new User("test1@test.com", "Password1234");
		user.setId(111L);
		when(userRepository.existsById(anyLong())).thenReturn(true);

		userDetailService.deleteUserById(user.getId());

		Mockito.verify(userRepository).existsById(anyLong());
		Mockito.verify(userRepository).deleteById(anyLong());
	}

}
