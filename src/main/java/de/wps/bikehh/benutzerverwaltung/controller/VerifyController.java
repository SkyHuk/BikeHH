package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailsService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {

    private VerifyDetailsService _verifyDetailsService;

    @Autowired
    public VerifyController(PasswordDetailsService passwordDetailsService,VerifyDetailsService verifyDetailsService) {
        this._verifyDetailsService = verifyDetailsService;
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestVerificationMail(@RequestBody RequestMailModel requestModel) throws ApiRequestException {
        String email = requestModel.getEmail();
        _verifyDetailsService.requestVerificationMail(email);
    }

    @PutMapping
    public void verifyUser(@RequestParam String token) {
        _verifyDetailsService.verifyUser(token);
    }
}
