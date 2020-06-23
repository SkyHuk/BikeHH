package de.wps.bikehh.benutzerverwaltung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.benutzerverwaltung.material.Benutzer;

public interface UserAuthenticationRepository extends CrudRepository<Benutzer, Long> {

	boolean existsByEmailAdresse(String emailAdresse);

	Benutzer findByEmailAdresse(String emailAdresse);
}
