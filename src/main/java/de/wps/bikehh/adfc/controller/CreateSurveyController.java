package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfrage-erstellen")
public class CreateSurveyController {

	/**
	 * opens create_survey.html with optional coordinates. coordinates get set to 0
	 * if not present, are then omitted in javascript.
	 *
	 * @param coordinates coordinates if coming from map.html
	 * @param model       spring model
	 * @return html page
	 */
	@GetMapping
	public String showSurveyCreator(@ModelAttribute("bikehh_user") User user,
			@RequestParam(required = false, name = "coordinates") double[] coordinates, Model model) {
		if (coordinates != null) {
			model.addAttribute("lat", coordinates[0]);
			model.addAttribute("lng", coordinates[1]);
		} else {
			model.addAttribute("lat", 0);
			model.addAttribute("lng", 0);
		}
		model.addAttribute("user", user);
		return "adfc/create_survey";
	}

	/**
	 * Saves the json strings in a file directory
	 *
	 * @param body post body
	 * @return html page
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processSurvey(@RequestBody String body) {

		// create umfrage.json file
		Utils.saveJSONSurveyInFiles(body);

		// Survey survey = j.beanFrom(body);

		return "adfc/survey_list";
	}

}
