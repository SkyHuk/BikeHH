package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Reset;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordAuthenticationRepository extends CrudRepository<Reset, Long> {
    Optional<Reset> findByUserId(Long userId);
    Optional<Reset> findByToken(String token);
}
