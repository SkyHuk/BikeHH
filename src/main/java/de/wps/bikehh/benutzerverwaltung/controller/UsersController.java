package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.exception.ApiRequestException;
import de.wps.bikehh.benutzerverwaltung.service.BikehhUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private BikehhUserDetailsService _bikehhUserDetailsService;

    //@TODO autorize only admins

    @Autowired
    public UsersController(BikehhUserDetailsService bikehhUserDetailsService) {
        this._bikehhUserDetailsService = bikehhUserDetailsService;

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getAllUsers(@RequestHeader("Authorization") String accessToken) throws ApiRequestException {

    }

    //Get specific user
    @PostMapping(value = "/{id}")
    public void getUser(@PathVariable @NotNull int id) throws ApiRequestException {

    }

    //@TODO updated values as body
    @PutMapping(value = "/{id}")
    public void updateUser(@PathVariable @NotNull int id) throws ApiRequestException {

    }

    //@TODO can an admin delete another admin ?
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable @NotNull int id) throws ApiRequestException {

    }



}
