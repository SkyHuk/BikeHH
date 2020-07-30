package de.wps.bikehh.meldungen.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.meldungen.material.Meldung;

/**
 * Repository-Interface für Datenbank-Einbindung für Meldungen
 */
public interface MeldungenRepository extends CrudRepository<Meldung, Long> {

}
