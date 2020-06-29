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

import java.util.List;

@Service
public class AuthService {

    private SessionRepository _sessionRepository;
    private UserAuthenticationRepository _userAuthenticationRepository;

    @Autowired
    public AuthService(SessionRepository sessionRepository, UserAuthenticationRepository _userAuthenticationRepository) {
        this._sessionRepository = sessionRepository;
        this._userAuthenticationRepository = _userAuthenticationRepository;
    }

    //@Todo hash access token ?
    public Session loginUser(String email, String password) throws BadCredentialsException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
        }

        PasswordEncoderService hashService = new PasswordEncoderService();
        String hashedPassword = hashService.encodePassword(password);

        User user = _userAuthenticationRepository.findByEmailAddress(email);
        if (user.getIsLocked()) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }
        if (!user.getEncryptedPassword().equals(hashedPassword)) {
            throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
        }

        String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
        Session session = new Session(token, user);
        _sessionRepository.save(session);

        return session;
    }

    public void logoutUser(Session session) {
        _sessionRepository.delete(session);
    }

    public void logoutAllSession(Long userId) {
        List<Session> sessions = _sessionRepository.findAllByUserId(userId);
        for (Session s : sessions) {
            _sessionRepository.delete(s);
        }
    }

    public Session getSessionByToken(String token) {
        return _sessionRepository.findByToken(token).orElse(null);
    }
}
