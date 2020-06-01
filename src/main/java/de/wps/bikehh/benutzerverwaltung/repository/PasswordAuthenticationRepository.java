package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Reset;
import de.wps.bikehh.benutzerverwaltung.material.User;
import org.springframework.data.repository.CrudRepository;

public interface PasswordAuthenticationRepository extends CrudRepository<Reset, Long> {
    Reset findByUserId(Long userId);
    Reset findByToken(String token);
}
