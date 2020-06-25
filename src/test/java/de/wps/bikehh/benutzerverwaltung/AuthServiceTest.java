package de.wps.bikehh.benutzerverwaltung;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import static org.junit.Assert.assertThat;

import de.wps.bikehh.benutzerverwaltung.service.PasswordEncoderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    SessionRepository _sessionRepository;

    @Mock
    UserAuthenticationRepository _userAuthenticationRepository;

    @InjectMocks
    AuthService _authService;

    @Test(expected = ApiRequestException.class)
    public void testLoginUser() {
        String testEmail = "test@mail.com";
        String testPw = "test";
        PasswordEncoderService hashService = new PasswordEncoderService();
        String testPwHash = hashService.encodePassword(testPw);

        User testUser = new User(testEmail, testPwHash);

        when(_userAuthenticationRepository.existsByEmailAddress(testEmail)).thenReturn(true);
        when(_userAuthenticationRepository.findByEmailAddress(testEmail)).thenReturn(testUser);
        when(_sessionRepository.save(any(Session.class))).thenReturn(new Session());

        // login with valid credentials
        assertThat(_authService.loginUser(testEmail, testPw), is(notNullValue()));

        // login with invalid credentials
        _authService.loginUser(testEmail, "wrongPw");

        // login with no existing account
        _authService.loginUser("no@mail.com", "noPw");

        // login into locked user
        testUser.setIsLocked(true);
        _authService.loginUser(testEmail, testPw);
    }

    @Test
    public void testLogoutUser() {
        _authService.logoutUser(new Session());
        verify(_sessionRepository, atLeastOnce()).delete(any(Session.class));
    }

    @Test
    public void testLogoutAllSession() {
        Long testId = 14524L;
        List<Session> sessionsByUser = Arrays.asList(new Session(), new Session(), new Session());
        when(_sessionRepository.findAllByUserId((long) testId)).thenReturn(sessionsByUser);

        _authService.logoutAllSession(testId);
        verify(_sessionRepository, times(3)).delete(any(Session.class));
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
