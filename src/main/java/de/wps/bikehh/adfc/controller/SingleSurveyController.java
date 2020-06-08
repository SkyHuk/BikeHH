package de.wps.bikehh.adfc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.wps.bikehh.adfc.material.SurveyTest;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfrage")
public class SingleSurveyController {

	/**
	 * 
	 * opens html page for specific survey
	 * 
	 * @param model        spring model
	 * @param surveyNumber number of survey to open
	 * @return html page
	 */
	@GetMapping
	public String showSingleSurvey(Model model, @RequestParam(name = "values") int surveyNumber) {

		List<SurveyTest> surveysList = Utils.getSurveyJsonsAsArray();
		for (SurveyTest survey : surveysList) {
			if (survey.getId() == surveyNumber) {
				model.addAttribute("survey", survey);
			}
		}

		return "adfc/survey";
	}
}
