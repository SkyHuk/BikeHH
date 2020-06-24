package de.wps.bikehh.umfragen.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.umfragen.material.Umfrage;

public interface UmfrageRepository extends CrudRepository<Umfrage, Integer> {
}