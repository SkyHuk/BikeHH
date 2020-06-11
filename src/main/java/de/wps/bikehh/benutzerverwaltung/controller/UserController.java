package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestMailModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private BikehhUserDetailsService _bikehhUserDetailsService;
    private VerifyDetailsService _verifyDetailsService;

    @Autowired
    public UserController(BikehhUserDetailsService bikehhUserDetailsService, VerifyDetailsService verifyDetailsService) {
        this._bikehhUserDetailsService = bikehhUserDetailsService;
        this._verifyDetailsService = verifyDetailsService;
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDetailsResponseModel getCurrentUser(Authentication auth) throws ApiRequestException {
        return _bikehhUserDetailsService.getCurrentUser(auth);
    }

    //@TODO how do we create admin user ? through different endpoint ?
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();

        _bikehhUserDetailsService.createUser(email, password);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(Authentication auth, @RequestBody UpdateUserDetailsRequestModel requestUserDetails) {
        _bikehhUserDetailsService.updateUser(auth, requestUserDetails);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication auth) {
        _bikehhUserDetailsService.deleteUser(auth);
    }

    @RequestMapping(value = "/password",method = RequestMethod.PUT)
    public void updatePassword() {
        //@TODO
    }

    @RequestMapping("/verify")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void requestVerificationMail(@RequestBody RequestMailModel requestModel) throws ApiRequestException {
        String email = requestModel.getEmail();
        _verifyDetailsService.requestVerificationMail(email);
    }


    @RequestMapping(value = "/verify",method = RequestMethod.PUT)
    public void verifyUser(@RequestParam String token) {
        _verifyDetailsService.verifyUser(token);
    }
}
