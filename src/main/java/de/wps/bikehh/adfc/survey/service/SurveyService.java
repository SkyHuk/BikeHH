package de.wps.bikehh.adfc.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.wps.bikehh.adfc.material.Survey;
import de.wps.bikehh.adfc.survey.repository.SurveyRepository;

public class SurveyService {

	@Autowired
	SurveyRepository surveyRepository;

	public List<Survey> getAllSurveys() {
		List<Survey> surveys = new ArrayList<Survey>();
		surveyRepository.findAll().forEach(survey -> surveys.add(survey));
		return surveys;
	}

	public Survey getSurveyById(int id) {
		return surveyRepository.findById((long) id).get();
	}

	public void saveOrUpdate(Survey survey) {
		surveyRepository.save(survey);
	}

	public void delete(int id) {
		surveyRepository.deleteById((long) id);
	}
}
