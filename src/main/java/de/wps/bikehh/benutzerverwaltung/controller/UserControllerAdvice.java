package de.wps.bikehh.benutzerverwaltung.controller;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import de.wps.bikehh.benutzerverwaltung.material.User;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    SessionRepository sessionRepository;

    @ModelAttribute(name = "bikehh_user", binding = false)
    public User getUser(Authentication authentication) {


        String token = (String) authentication.getPrincipal();
        Session session = sessionRepository.findByToken(token).orElse(null);
        if (session == null) {
            return null;
        }

        return session.getUser();
    }

}
