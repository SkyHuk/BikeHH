package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.material.User;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {

    Session findByUser(User user);
    Session findByToken(String token);
}
