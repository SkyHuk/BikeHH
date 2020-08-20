package de.wps.bikehh.verifizierung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.verifizierung.material.Verification;

public interface VerificationRepository extends CrudRepository<Verification, Long> {

	boolean existsByUser(User user);

	Verification findByUser(User user);

	void deleteByUser(User user);

	boolean existsByToken(String token);

	Verification findByToken(String token);
}
