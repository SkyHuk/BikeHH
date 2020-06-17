package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.PasswordRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private BikehhUserDetailsService _bikehhUserDetailsService;

    @Autowired
    public UserController(BikehhUserDetailsService bikehhUserDetailsService) {
        this._bikehhUserDetailsService = bikehhUserDetailsService;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDetailsResponseModel getCurrentUser(Authentication auth) throws ApiRequestException {
        User user = (User) auth.getPrincipal();
        return _bikehhUserDetailsService.getCurrentUser(user);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@Valid @RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();

        _bikehhUserDetailsService.createUser(email, password);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(Authentication auth, @Valid @RequestBody UpdateUserDetailsRequestModel requestUserDetails) {
        User user = (User) auth.getPrincipal();

        _bikehhUserDetailsService.updateUser(user, requestUserDetails);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication auth) {
        User user = (User) auth.getPrincipal();

        _bikehhUserDetailsService.deleteUser(user);
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public void updatePassword(Authentication auth, @Valid @RequestBody PasswordRequestModel passwordRequestModel) {
        User user = (User) auth.getPrincipal();

        String passwordOld = passwordRequestModel.getOldPassword();
        String passwordNew = passwordRequestModel.getNewPassword();

        _bikehhUserDetailsService.updatePassword(user, passwordOld, passwordNew);
    }
}
