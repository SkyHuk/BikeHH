package de.wps.bikehh.benutzerverwaltung.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;

@ControllerAdvice
public class UserControllerAdvice {

	@ModelAttribute(name = "user", binding = false)
	public User getBenutzer(Authentication authentication) {
		if (authentication == null) {
			return null;
		}

		if (authentication.getPrincipal() instanceof BikehhUserDetails) {
			BikehhUserDetails user = (BikehhUserDetails) authentication.getPrincipal();
			return user.getBikehhUser();
		} else {
			return (User) authentication.getPrincipal();
		}
	}
}