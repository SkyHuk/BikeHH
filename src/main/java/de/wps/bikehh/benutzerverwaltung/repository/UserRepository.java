package de.wps.bikehh.benutzerverwaltung.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.benutzerverwaltung.material.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	boolean existsByEmailAddress(String emailAdresse);

	User findByEmailAddress(String emailAdresse);
}
