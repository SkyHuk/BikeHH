package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.PasswordRequestModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import de.wps.bikehh.benutzerverwaltung.service.PasswordDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {


    private PasswordDetailsService _passwordDetailsService;
    private BikehhUserDetailsService _bikehhUserDetailsService;

    @Autowired
    public PasswordController(PasswordDetailsService passwordDetailsService, BikehhUserDetailsService bikehhUserDetailsService) {
        this._passwordDetailsService = passwordDetailsService;
        this._bikehhUserDetailsService = bikehhUserDetailsService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestPasswordVerificationMail(@RequestBody PasswordRequestModel requestModel) throws ApiRequestException {

    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@RequestParam String token) {

    }


}
