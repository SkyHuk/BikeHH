package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.repository.PasswordAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordDetailsService {

    private PasswordAuthenticationRepository _passwordAuthenticationRepository;

    @Autowired
    public PasswordDetailsService(PasswordAuthenticationRepository passwordAuthenticationRepository) {
        this._passwordAuthenticationRepository = passwordAuthenticationRepository;
    }

    public void createToken(Long userId) throws ApiRequestException {
        /*if (_passwordAuthenticationRepository.existsByUserId(userId)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }*/
        String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
        Reset resetToken = new Reset(userId, token);
        _passwordAuthenticationRepository.save(resetToken);
    }

}
