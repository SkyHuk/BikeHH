package de.wps.bikehh.beantwortung.repository;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.beantwortung.material.Beantwortung;
import de.wps.bikehh.befragungen.material.Frage;

@Repository
public interface BeantwortungRepository extends CrudRepository<Beantwortung, Long> {

	Stream<Beantwortung> findAllByFrage(Frage frage);

}
