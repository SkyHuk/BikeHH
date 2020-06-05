package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private SessionRepository _sessionRepository;
    private UserAuthenticationRepository _userAuthenticationRepository;

    @Autowired
    public AuthService(SessionRepository sessionRepository, UserAuthenticationRepository _userAuthenticationRepository) {
        this._sessionRepository = sessionRepository;
        this._userAuthenticationRepository = _userAuthenticationRepository;
    }

    public Session loginUser(String email, String password) throws BadCredentialsException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new BadCredentialsException("Bad credentials");
        }

        User user = _userAuthenticationRepository.findByEmailAddress(email);
        if (user.getEncryptedPassword() != password) {
            throw new BadCredentialsException("Bad credentials");
        }

        String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
        Session session = new Session(token, user);
        _sessionRepository.save(session);

        return session;
    }

    public void logoutUser(String token) {
        Session session = _sessionRepository.findByToken(token);
        _sessionRepository.delete(session);
    }

    public User getUserByToken(String token) {
        return _sessionRepository.findByToken(token).getUser();
    }
}
