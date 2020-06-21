package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {

    private VerifyDetailService _verifyDetailService;


    @Autowired
    public VerifyController(VerifyDetailService verifyDetailService) {
        this._verifyDetailService = verifyDetailService;
    }


    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestVerificationMail(@Valid @RequestBody RequestMailModel requestModel) throws ApiRequestException {
        String email = requestModel.getEmail();
        _verifyDetailService.requestVerificationMail(email);
    }

    @PutMapping
    public void verifyUser(@RequestParam String token) {
        _verifyDetailService.verifyUser(token);
    }
}
