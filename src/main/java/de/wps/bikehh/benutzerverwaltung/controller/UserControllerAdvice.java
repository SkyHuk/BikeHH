package de.wps.bikehh.benutzerverwaltung.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.User;

/*
@ControllerAdvice
public class UserControllerAdvice {

	@ModelAttribute(name = "bikehh_user", binding = false)
	public User getUser(Authentication authentication) {
		*/
/*System.out.println("---------------------------------------------------");
		System.out.println(authentication.getAuthorities()); //[user]
		System.out.println(authentication.getCredentials()); // null
		System.out.println(authentication.getDetails()); //SessionID
		System.out.println(authentication.getPrincipal()); //Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: user
		System.out.println(authentication.getName()); //emre*//*

		if (authentication == null) {
			return null;
		}

		BikehhUserDetails userDetails = (BikehhUserDetails) authentication.getPrincipal();
		return userDetails.getBikehhUser();
	}

}
*/
