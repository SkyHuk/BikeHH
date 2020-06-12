package de.wps.bikehh.benutzerverwaltung.repository;

import de.wps.bikehh.benutzerverwaltung.material.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    List<Session> findAllByUserId(Long userId);

}
