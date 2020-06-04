package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.Validation.Validation;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.service.BikehhPasswordEncoderService;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;

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
    public UserDetailsResponseModel getCurrentUser(@RequestHeader("Authorization") String accessToken) throws ApiRequestException {
        BikehhUserDetails user = (BikehhUserDetails) _bikehhUserDetailsService.loadUserByUsername(accessToken);

        User u = user.getBikehhUser();
        if (u == null) {
            throw new ApiRequestException(ErrorCode._not_found, HttpStatus.NOT_FOUND);
        }

        return new UserDetailsResponseModel(u);

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsRequestModel createUser(@RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();

        if (!Validation.isEmailValid(email) || !Validation.isPasswordValid(password)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        User user = new User(email, password);
        user.setRole(Roles.ROLE_USER);

        BikehhPasswordEncoderService encoder = new BikehhPasswordEncoderService();
        user.setEncryptedPassword(encoder.encodePassword(requestUserDetails.getPassword()));

        _bikehhUserDetailsService.createUser(user);
        return requestUserDetails;
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(@RequestHeader("Authorization") String accessToken, @RequestBody UpdateUserDetailsRequestModel requestUserDetails) {
        //_bikehhUserDetailsService.updateUser(getCurrentUser(accessToken), requestUserDetails);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader("Authorization") String accessToken, @RequestBody User user) {
        //_bikehhUserDetailsService.deleteUser(getCurrentUser(accessToken));
    }
}
