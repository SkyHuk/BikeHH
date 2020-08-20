package de.wps.bikehh.verifizierung.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.verifizierung.material.Verification;

public interface VerificationRepository extends CrudRepository<Verification, Long> {

	boolean existsByUser(User user);

	Optional<Verification> findByUser(User user);

	void deleteByUser(User user);

	Optional<Verification> findByToken(String token);
}
