package de.wps.bikehh.authentifizierung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.authentifizierung.material.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {
    Optional<Session> findByToken(String token);
    List<Session> findAllByUserId(Long userId);

}
