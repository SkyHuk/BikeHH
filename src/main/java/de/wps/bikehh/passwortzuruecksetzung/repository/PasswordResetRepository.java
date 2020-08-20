package de.wps.bikehh.passwortzuruecksetzung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.passwortzuruecksetzung.material.Reset;

import java.util.Optional;

public interface PasswordResetRepository extends CrudRepository<Reset, Long> {
    Optional<Reset> findByUserId(Long userId);
    Optional<Reset> findByToken(String token);
}
