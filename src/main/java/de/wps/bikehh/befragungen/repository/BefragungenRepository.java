package de.wps.bikehh.befragungen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.befragungen.material.Befragung;

@Repository
public interface BefragungenRepository extends CrudRepository<Befragung, Long> {

}
