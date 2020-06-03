package de.wps.bikehh.adfc.survey.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.adfc.material.Survey;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}