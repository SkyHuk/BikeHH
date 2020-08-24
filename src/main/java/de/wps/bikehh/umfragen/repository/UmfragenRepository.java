package de.wps.bikehh.umfragen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.umfragen.material.Umfrage;

@Repository
public interface UmfragenRepository extends CrudRepository<Umfrage, Long> {

}
