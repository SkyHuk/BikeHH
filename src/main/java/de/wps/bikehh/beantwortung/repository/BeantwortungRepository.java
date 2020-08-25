package de.wps.bikehh.beantwortung.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.wps.bikehh.beantwortung.material.Beantwortung;
import de.wps.bikehh.befragungen.material.Frage;

@Repository
public interface BeantwortungRepository extends CrudRepository<Beantwortung, Long> {

	List<Beantwortung> findAllByFrage(Frage frage);

}
