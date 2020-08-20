package de.wps.bikehh.benutzerverwaltung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.benutzerverwaltung.material.User;

public interface UserRepository extends CrudRepository<User, Long> {

	boolean existsByEmailAddress(String emailAdresse);

	User findByEmailAddress(String emailAdresse);
}
