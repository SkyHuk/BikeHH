package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestPasswordResetMail(@Valid @RequestBody RequestMailModel requestModel) throws ApiRequestException {
        String email = requestModel.getEmail();

        _passwordDetailService.requestResetMail(email);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@Valid @RequestBody ResetPasswordModel requestModel, @RequestParam String token) {
        String password = requestModel.getNewPassword();

        _passwordDetailService.resetPassword(password, token);
    }


}
