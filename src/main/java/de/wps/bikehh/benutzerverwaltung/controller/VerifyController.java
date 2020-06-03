package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {


    private VerifyDetailsService _verifyDetailsService;
    private BikehhUserDetailsService _bikehhUserDetailsService;

    @Autowired
    public VerifyController(VerifyDetailsService verifyDetailsService, BikehhUserDetailsService bikehhUserDetailsService) {
        this._verifyDetailsService = verifyDetailsService;
        this._bikehhUserDetailsService = bikehhUserDetailsService;

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestVerificationMail(@RequestBody RequestMailModel requestModel) throws ApiRequestException {
        BikehhUserDetails userDetails;
        try {
            userDetails = (BikehhUserDetails) _bikehhUserDetailsService.loadUserByUsername(requestModel.getEmail());
        } catch (Exception e) {
            return;
        }

        User u = userDetails.getBikehhUser();
        if (u == null || u.getIsVerified()) {
            return;
        }

        _verifyDetailsService.requestVerificationMail(u);
    }

    @PutMapping
    public void verifyUser(@RequestParam String token) {
        _verifyDetailsService.verifyUser(token);
    }


}
