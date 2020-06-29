package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.LoginRequest;
import de.wps.bikehh.benutzerverwaltung.dto.response.SessionResponseModel;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.security.OAuthToken;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService _authService;

    @Autowired
    public AuthController(AuthService authService) {
        this._authService = authService;
    }

    //@TODO restriction for how many devices to log in ?
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public SessionResponseModel login(@Valid @RequestBody LoginRequest loginrequest) {
        String email = loginrequest.getEmail();
        String password = loginrequest.getPassword();

        Session session = _authService.loginUser(email, password);
        return new SessionResponseModel(session.getToken());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(Authentication auth) {
        Session session = ((OAuthToken) auth).getSession();

        _authService.logoutUser(session);
    }
}
