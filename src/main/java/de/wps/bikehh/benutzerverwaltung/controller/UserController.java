package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.PasswordRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDetailService _UserDetailService;

    @Autowired
    public UserController(UserDetailService userDetailService) {
        this._UserDetailService = userDetailService;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDetailsResponseModel getCurrentUser(Authentication auth) throws ApiRequestException {
        User user = (User) auth.getPrincipal();
        return _UserDetailService.getCurrentUser(user);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@Valid @RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();

        _UserDetailService.createUser(email, password);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(Authentication auth, @Valid @RequestBody UpdateUserDetailsRequestModel requestUserDetails) {
        User user = (User) auth.getPrincipal();

        _UserDetailService.updateUser(user, requestUserDetails);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication auth) {
        User user = (User) auth.getPrincipal();

        _UserDetailService.deleteUser(user);
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public void updatePassword(Authentication auth, @Valid @RequestBody PasswordRequestModel passwordRequestModel) {
        User user = (User) auth.getPrincipal();

        String passwordOld = passwordRequestModel.getOldPassword();
        String passwordNew = passwordRequestModel.getNewPassword();

        _UserDetailService.updatePassword(user, passwordOld, passwordNew);
    }
}
