package de.wps.bikehh.benutzerverwaltung.service;

import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.PasswordAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.service.smtp.Mail;
import de.wps.bikehh.benutzerverwaltung.service.smtp.SmtpService;
import de.wps.bikehh.benutzerverwaltung.utils.Utils;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class PasswordDetailsService {

    private PasswordAuthenticationRepository _passwordAuthenticationRepository;
    private UserAuthenticationRepository _userAuthenticationRepository;
    private SmtpService _smtpService;

    @Autowired
    public PasswordDetailsService(PasswordAuthenticationRepository passwordAuthenticationRepository, UserAuthenticationRepository userAuthenticationRepository, SmtpService smtpService) {
        this._passwordAuthenticationRepository = passwordAuthenticationRepository;
        this._userAuthenticationRepository = userAuthenticationRepository;
        this._smtpService = smtpService;
    }

    public void requestResetMail(String email) throws ApiRequestException {
        if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
            return;
        }

        User user = _userAuthenticationRepository.findByEmailAddress(email);

        //Delete in case token for user already exists
        Reset reset = _passwordAuthenticationRepository.findByUserId(user.getId());
        if (reset != null) {
            _passwordAuthenticationRepository.delete(reset);
        }

        String token = Utils.generateSecureToken(Utils.TOKEN_COUNT);
        Reset resetToken = new Reset(user.getId(), token);


        _passwordAuthenticationRepository.save(resetToken);

        //Send mail
        Mail mail = new Mail(user.getEmailAddress(), "Reset password");

        String redirectLink = String.format("http://localhost:8080/api/password?token=%s", token);

        Map<String, String> model = new HashMap<>();
        //model.put("username", user.getEmailAddress());
        model.put("link", redirectLink);
        mail.setModel(model);

        _smtpService.sendMail(mail, SmtpService.Templates.RESET);

    }

    public void resetPassword(String password, String token) throws ApiRequestException {
        Reset reset = _passwordAuthenticationRepository.findByToken(token).orElse(null);
        if (reset == null) {
            throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
        }

        User user = _userAuthenticationRepository.findById(reset.getUserId()).orElse(null);
        if (user == null) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }


        BikehhPasswordEncoderService _encoder = new BikehhPasswordEncoderService();
        String encodedPassword = _encoder.encode(password);
        user.setEncryptedPassword(encodedPassword);

        //Delete reset token and set new password
        _passwordAuthenticationRepository.delete(reset);
        _userAuthenticationRepository.save(user);
    }

    @Scheduled(fixedRate = 10000)
    public void deleteExpiredTokens() {
        List<Reset> list = new ArrayList<>();
        _passwordAuthenticationRepository.findAll().forEach(list::add);

        Date now = new Date();
        long oneHour = 1000 * 60 * 60;

        for (Reset r : list) {
            if ((now.getTime() - r.getCreatedAt().getTime()) > oneHour) {
                _passwordAuthenticationRepository.delete(r);
            }
        }
    }

}
