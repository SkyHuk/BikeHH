package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Reset;
import org.springframework.data.repository.CrudRepository;

public interface PasswordAuthenticationRepository extends CrudRepository<Reset, Long> {

    boolean existsByUserId(String userId);

    Reset findByUserId(String userId);
}
