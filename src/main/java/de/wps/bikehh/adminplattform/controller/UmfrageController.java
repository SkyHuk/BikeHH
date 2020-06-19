package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.wps.bikehh.adminplattform.material.UmfrageDB;
import de.wps.bikehh.adminplattform.service.SurveyService;

public class UmfrageController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveys")
	private List<UmfrageDB> getAllSurveys() {
		return surveyService.getAllSurveys();
	}

	@GetMapping("/surveys/{id}")
	private UmfrageDB getSurvey(@PathVariable("id") int id) {
		return surveyService.getSurveyById(id);
	}

	@DeleteMapping("/surveys/{id}")
	private void deleteSurvey(@PathVariable("id") int id) {
		surveyService.delete(id);
	}

	@PostMapping("/surveys")
	private int saveSurvey(@RequestBody UmfrageDB survey) {
		surveyService.saveOrUpdate(survey);
		return survey.getId();
	}
}
