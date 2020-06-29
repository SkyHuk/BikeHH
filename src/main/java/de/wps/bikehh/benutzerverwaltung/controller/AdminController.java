package de.wps.bikehh.benutzerverwaltung.controller;

//Deprecated

/*

import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUsersDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsRequestModel;
import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private UserDetailService _UserDetailService;

    @Autowired
    public AdminController(UserDetailService userDetailService) {
        this._UserDetailService = userDetailService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAdmin(Authentication auth, @Valid @RequestBody UserDetailsRequestModel requestUserDetails) {
        this.authorizeAsAdmin(auth);

        String email = requestUserDetails.getEmail();
        String password = requestUserDetails.getPassword();
        _UserDetailService.createAdmin(email, password);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(Authentication auth) {
        this.authorizeAsAdmin(auth);

        return _UserDetailService.retrieveUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(Authentication auth, @PathVariable("id") @NotNull Long id) {
        this.authorizeAsAdmin(auth);
        return _UserDetailService.getUserById(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(Authentication auth, @PathVariable("id") @NotNull Long id, @RequestBody UpdateUsersDetailsRequestModel userModel) {
        this.authorizeAsAdmin(auth);
        _UserDetailService.updateUserById(id, userModel);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Authentication auth, @PathVariable("id") @NotNull Long id) {
        this.authorizeAsAdmin(auth);

        _UserDetailService.deleteUserById(id);
    }

    private void authorizeAsAdmin(Authentication auth) {
        User currentUser = (User) auth.getPrincipal();
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            throw new ApiRequestException(ErrorCode.forbidden, HttpStatus.FORBIDDEN);
        }
    }
}
*/
