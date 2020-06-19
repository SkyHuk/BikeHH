package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfragen/{surveyId}")
public class UmfrageDetailController {

	/**
	 *
	 * opens html page for specific survey
	 *
	 * @param model    spring model
	 * @param surveyId survey id to open
	 * @return html page
	 */
	@GetMapping
	public String showSingleSurvey(Model model, @PathVariable Integer surveyId) {

		List<Umfrage> surveysList = Utils.getSurveyJsonsAsArray();
		for (Umfrage survey : surveysList) {
			if (survey.getId() == surveyId) {
				model.addAttribute("survey", survey);
			}
		}

		return "adfc/survey";
	}
}
