package de.wps.bikehh.meldungen.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.meldungen.material.Meldung;

public interface MeldungRepository extends CrudRepository<Meldung, Long> {

}
