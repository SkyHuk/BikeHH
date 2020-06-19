package de.wps.bikehh.adminplattform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.wps.bikehh.adminplattform.material.UmfrageDB;
import de.wps.bikehh.adminplattform.repository.UmfrageRepository;

public class SurveyService {

	@Autowired
	UmfrageRepository surveyRepository;

	public List<UmfrageDB> getAllSurveys() {
		List<UmfrageDB> surveys = new ArrayList<UmfrageDB>();
		surveyRepository.findAll().forEach(survey -> surveys.add(survey));
		return surveys;
	}

	public UmfrageDB getSurveyById(int id) {
		return surveyRepository.findById((long) id).get();
	}

	public void saveOrUpdate(UmfrageDB survey) {
		surveyRepository.save(survey);
	}

	public void delete(int id) {
		surveyRepository.deleteById((long) id);
	}
}
