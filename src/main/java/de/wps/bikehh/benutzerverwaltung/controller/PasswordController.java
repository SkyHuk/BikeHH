package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.ResetPasswordModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;
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
    public void requestPasswordResetMail(@RequestBody RequestMailModel requestModel) throws ApiRequestException {
        BikehhUserDetails userDetails;

        userDetails = (BikehhUserDetails) _bikehhUserDetailsService.loadUserByUsername(requestModel.getEmail());


        User u = userDetails.getBikehhUser();
        if (u == null) {
            return;
        }

        _passwordDetailsService.requestResetMail(u);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@RequestBody ResetPasswordModel requestModel, @RequestParam String token) {
        _passwordDetailsService.resetPassword(requestModel, token);
    }


}
