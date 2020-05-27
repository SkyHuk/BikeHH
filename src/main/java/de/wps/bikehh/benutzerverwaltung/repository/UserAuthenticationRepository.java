package de.wps.bikehh.benutzerverwaltung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.benutzerverwaltung.material.User;

public interface UserAuthenticationRepository extends CrudRepository<User, Long> {

	boolean existsByEmailAddress(String emailAddress);

	User findByEmailAddress(String emailAddress);
}
