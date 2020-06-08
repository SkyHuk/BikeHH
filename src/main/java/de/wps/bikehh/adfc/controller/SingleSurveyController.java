package de.wps.bikehh.adfc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.wps.bikehh.adfc.material.SurveyTest;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfragen/{surveyId}")
public class SingleSurveyController {

	/**
	 * 
	 * opens html page for specific survey
	 * 
	 * @param model        spring model
	 * @param surveyId survey id to open
	 * @return html page
	 */
	@GetMapping
	public String showSingleSurvey(Model model, @PathVariable Integer surveyId) {

		List<SurveyTest> surveysList = Utils.getSurveyJsonsAsArray();
		for (SurveyTest survey : surveysList) {
			if (survey.getId() == surveyId) {
				model.addAttribute("survey", survey);
			}
		}

		return "adfc/survey";
	}
}
