package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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

    //@Todo hash access token
    public Session loginUser(String email, String password) throws BadCredentialsException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
            throw new BadCredentialsException("Bad credentials");
        }

        BikehhPasswordEncoderService hashService = new BikehhPasswordEncoderService();
        String hashedPassword = hashService.encodePassword(password);

        User user = _userAuthenticationRepository.findByEmailAddress(email);
        if (!user.getEncryptedPassword().equals(hashedPassword)) {
            throw new BadCredentialsException("Bad credentials");
        }

        String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
        Session session = new Session(token, user);
        _sessionRepository.save(session);

        return session;
    }

    public void logoutUser(Session session) {
        _sessionRepository.delete(session);
    }

    public void logoutAllSession(User user) {
        List<Session> sessions = _sessionRepository.findAllByUserId(user.getId());
        System.out.println(sessions.size());
        for (Session s : sessions) {
            _sessionRepository.delete(s);
        }
    }

    public Session getSessionByToken(String token) {
        return _sessionRepository.findByToken(token).orElse(null);
    }
}
