package de.wps.bikehh.befragungen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.befragungen.material.Frage;

@Repository
public interface FragenRepository extends CrudRepository<Frage, Long> {

}
