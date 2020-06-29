package de.wps.bikehh.benutzerverwaltung;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUsersDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailService;
import de.wps.bikehh.benutzerverwaltung.service.PasswordEncoderService;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailService;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailServiceTest {

	// @Autowired
	@Mock
	AuthService authService;
	@Mock
	VerifyDetailService verifyDetailService;
	@Mock
	PasswordDetailService passwordDetailService;

	@Mock
	private UserAuthenticationRepository userRepository;
	@InjectMocks
	private UserDetailService userDetailService;

	// ------------------

	@Test
	public void testCreateUser() {
		userDetailService.createUser("test@web.de", "Test1234");
		Mockito.verify(userRepository).existsByEmailAddress(anyString());
		Mockito.verify(userRepository).save(Mockito.any(User.class));
	}

	@Test
	public void testUpdateUser() {

		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		User user = new User();
		UpdateUserDetailsRequestModel userUpdate = new UpdateUserDetailsRequestModel();
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
	public void testUpdatePassword() {

		when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
		User user = new User();
		PasswordEncoderService encoder = new PasswordEncoderService();
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

	@Test
	public void testUpdateUserById() {
		User user = new User("test2@test.com", "Password1234");
		user.setId(123L);
		when(userRepository.existsById(anyLong())).thenReturn(true);
		when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(new User());

		UpdateUsersDetailsRequestModel userUpdate = new UpdateUsersDetailsRequestModel();
		userUpdate.setIsLocked(false);
		assertThat(userDetailService.updateUserById(user.getId(), userUpdate), is(notNullValue()));
	}
}
