package de.wps.bikehh.passwortzuruecksetzung.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.passwortzuruecksetzung.material.Reset;

public interface PasswordResetRepository extends CrudRepository<Reset, Long> {

	Reset findByUserId(long userId);

	boolean existsByUserId(long userId);

	void deleteByUserId(long userId);

	Reset findByToken(String token);

	boolean existsByToken(String token);
}
