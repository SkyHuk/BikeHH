package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.PasswordAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.smtp.Mail;
import de.wps.bikehh.benutzerverwaltung.service.smtp.SmtpService;
import de.wps.bikehh.benutzerverwaltung.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerifyDetailsService {

    private PasswordAuthenticationRepository _passwordAuthenticationRepository;
    private UserAuthenticationRepository _userAuthenticationRepository;
    private SmtpService _smtpService;

    @Autowired
    public VerifyDetailsService(PasswordAuthenticationRepository passwordAuthenticationRepository, UserAuthenticationRepository userAuthenticationRepository, SmtpService smtpService, BikehhPasswordEncoderService bikehhPasswordEncoderService) {
        this._passwordAuthenticationRepository = passwordAuthenticationRepository;
        this._userAuthenticationRepository = userAuthenticationRepository;
        this._smtpService = smtpService;
    }

    public void requestVerificationMail(User user) {
    }

    public void verifyUser(ResetPasswordModel requestModel, String token) throws ApiRequestException {

    }
}
