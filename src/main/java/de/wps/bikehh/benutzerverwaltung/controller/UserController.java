package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.dto.request.PasswordRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserDetailsResponseModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import de.wps.bikehh.benutzerverwaltung.util.Validation;
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

    /**
     *
     * gebt den aktuellen User zurück
     *
     * @param auth der aktuelle authentifizierte User
     * @return User
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDetailsResponseModel getCurrentUser(Authentication auth) throws ApiRequestException {
        User user = (User) auth.getPrincipal();
        return new UserDetailsResponseModel(user);
    }

    /**
     *
     * registriert einen neuen User
     *
     * @param requestUserDetails User credentials
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@Valid @RequestBody UserDetailsRequestModel requestUserDetails) throws ApiRequestException {
        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();

        if (!Validation.isEmailValid(email) || !Validation.isPasswordValid(password)) {
            throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
        }

        _UserDetailService.createUser(email, password);
    }

    /**
     *
     * updated einen existierenden User
     *
     * @param requestUserDetails die veränderten Userdaten
     */
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUser(Authentication auth, @Valid @RequestBody UpdateUserDetailsRequestModel requestUserDetails) {
        User user = (User) auth.getPrincipal();

        _UserDetailService.updateUser(user, requestUserDetails);
    }

    /**
     *
     * löscht einen User Account
     *
     * @param auth der aktuelle authentifizierte User
     */
    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication auth) {
        User user = (User) auth.getPrincipal();

        _UserDetailService.deleteUser(user);
    }

    /**
     *
     * setzt ein neues Passwort für ein schon eingeloggter User
     *
     * @param auth der aktuelle authentifizierte User
     * @param passwordRequestModel das neue Passwort
     */
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public void updatePassword(Authentication auth, @Valid @RequestBody PasswordRequestModel passwordRequestModel) {
        User user = (User) auth.getPrincipal();

        String passwordOld = passwordRequestModel.getOldPassword();
        String passwordNew = passwordRequestModel.getNewPassword();


        if (!Validation.isPasswordValid(passwordNew)) {
            throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
        }

        _UserDetailService.updatePassword(user, passwordOld, passwordNew);
    }
}
