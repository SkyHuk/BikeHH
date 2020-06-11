package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.exception.ErrorCode;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private BikehhUserDetailsService _bikehhUserDetailsService;

    @Autowired
    public UsersController(BikehhUserDetailsService bikehhUserDetailsService) {
        this._bikehhUserDetailsService = bikehhUserDetailsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(Authentication auth) {
        this.authorizeAsAdmin(auth);

        return _bikehhUserDetailsService.retrieveUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(Authentication auth, @PathVariable @NotNull Long id) {
        this.authorizeAsAdmin(auth);

        return _bikehhUserDetailsService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    public void updateUser(Authentication auth, @PathVariable @NotNull Long id, @RequestBody User updatedUser) {
        this.authorizeAsAdmin(auth);

        _bikehhUserDetailsService.updateUserById(id, updatedUser);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(Authentication auth, @PathVariable @NotNull Long id) {
        this.authorizeAsAdmin(auth);

        _bikehhUserDetailsService.deleteUserById(id);
    }

    private void authorizeAsAdmin(Authentication auth) {
        User currentUser = (User) auth.getPrincipal();
        if (!currentUser.getRole().equals(Roles.ROLE_ADMIN)) {
            throw new ApiRequestException(ErrorCode.forbidden, HttpStatus.FORBIDDEN);
        }
    }
}
