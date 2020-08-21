package de.wps.bikehh.benutzerverwaltung;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import de.wps.bikehh.authentifizierung.material.Session;
import de.wps.bikehh.authentifizierung.repository.SessionRepository;
import de.wps.bikehh.authentifizierung.service.AuthenticationService;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

	@Mock
	SessionRepository _sessionRepository;

	@Mock
	UserRepository _userAuthenticationRepository;

	@InjectMocks
	AuthenticationService _authService;

	@Test
	public void testLogoutUser() {
		_authService.logoutUser(new Session());
		verify(_sessionRepository, atLeastOnce()).delete(any(Session.class));
	}

	@Test
	public void testGetSessionByToken() {
		String token = "abcdefghijklmnopqrstuvwxyz";
		when(_sessionRepository.findByToken(token)).thenReturn(java.util.Optional.of(new Session()));

		// get valid session
		assertThat(_authService.getSessionByToken(token), is(notNullValue()));

		// get no existing session
		assertThat(_authService.getSessionByToken("noExisting"), is(nullValue()));
	}
}
