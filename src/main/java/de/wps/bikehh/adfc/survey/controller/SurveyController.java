package de.wps.bikehh.adfc.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.wps.bikehh.adfc.material.Survey;
import de.wps.bikehh.adfc.survey.service.SurveyService;

public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveys")
	private List<Survey> getAllSurveys() {
		return surveyService.getAllSurveys();
	}

	@GetMapping("/surveys/{id}")
	private Survey getSurvey(@PathVariable("id") int id) {
		return surveyService.getSurveyById(id);
	}

	@DeleteMapping("/surveys/{id}")
	private void deleteSurvey(@PathVariable("id") int id) {
		surveyService.delete(id);
	}

	@PostMapping("/surveys")
	private int saveSurvey(@RequestBody Survey survey) {
		surveyService.saveOrUpdate(survey);
		return survey.getId();
	}
}
