package de.wps.bikehh.kategorien.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.kategorien.material.Kategorie;

@Repository
public interface KategorienRepository extends CrudRepository<Kategorie, Long> {

}
