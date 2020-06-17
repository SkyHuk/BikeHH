package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Verification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationAuthenticationRepository extends CrudRepository<Verification, Long> {
    Optional<Verification> findByUserId(Long userId);
    Optional<Verification> findByToken(String token);
}
