package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailService;
import de.wps.bikehh.benutzerverwaltung.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private PasswordDetailService _passwordDetailService;

    @Autowired
    public PasswordController(PasswordDetailService passwordDetailService) {
        this._passwordDetailService = passwordDetailService;
    }

    /**
     * schickt eine email raus, um sein Passwort zurückzusetzen
     *
     * @param requestModel email
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestPasswordResetMail(@Valid @RequestBody RequestMailModel requestModel) throws ApiRequestException {
        String email = requestModel.getEmail();

        if (!Validation.isEmailValid(email)) {
            throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
        }

        _passwordDetailService.requestResetMail(email);
    }

    /**
     * setzt ein neues Passwort
     *
     * @param requestModel neues Passwort
     * @param token eindeutiger Token, um User zu identifizieren
     */
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@Valid @RequestBody ResetPasswordModel requestModel, @RequestParam String token) {
        String password = requestModel.getNewPassword();

        _passwordDetailService.resetPassword(password, token);
    }


}
