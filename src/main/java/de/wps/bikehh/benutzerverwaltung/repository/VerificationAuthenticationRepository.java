package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Verification;
import org.springframework.data.repository.CrudRepository;

public interface VerificationAuthenticationRepository extends CrudRepository<Verification, Long> {
    Verification findByUserId(Long userId);

    Verification findByToken(String token);
}
