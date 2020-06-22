package de.wps.bikehh.benutzerverwaltung.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Benutzer;

@ControllerAdvice
public class UserControllerAdvice {

	@ModelAttribute(name = "bikehh_user", binding = false)
	public Benutzer getBenutzer(Authentication authentication) {
		if (authentication == null) {
			return null;
		}

		BikehhUserDetails userDetails = (BikehhUserDetails) authentication.getPrincipal();
		return userDetails.getBikehhUser();
	}

}
