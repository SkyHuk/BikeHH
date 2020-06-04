package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public User getCurrentUser(@RequestHeader("Authorization") String accessToken) {
        //User user = bikehhUserDetailsService.getUserbyAccesstoken(accessToken);
        //return user;
        //throw new TestRequestException("unauthorized", HttpStatus.UNAUTHORIZED);
        //UserDetails user = bikehhUserDetailsService.loadUserByUsername("emre");
        //return user.getPassword();
        return new User();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserDetailsRequestModel createUser(@RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        User user = new User(requestUserDetails.getEmail(), requestUserDetails.getPassword());
        if (user.getEmailAddress() == null || user.getEncryptedPassword() == null) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }
        user.setRole("user");
        _bikehhUserDetailsService.createUser(user);
        return requestUserDetails;
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(@RequestHeader("Authorization") String accessToken, @RequestBody UpdateUserDetailsRequestModel requestUserDetails){
        _bikehhUserDetailsService.updateUser(getCurrentUser(accessToken), requestUserDetails);
        }


    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader("Authorization") String accessToken, @RequestBody User user) {
    _bikehhUserDetailsService.deleteUser(getCurrentUser(accessToken));
    }



}
