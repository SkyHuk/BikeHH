package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("umfrage-erstellen")
public class CreateSurveyController {

	@GetMapping
	public String showSurveyCreator(@RequestParam(required = false, name = "coordinates") double[] coordinates,
			Model model) {
		if (coordinates != null) {
			model.addAttribute("lat", coordinates[0]);
			model.addAttribute("lng", coordinates[1]);
		} else {
			model.addAttribute("lat", 0);
			model.addAttribute("lng", 0);
		}

		return "adfc/create_survey";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSurvey(@RequestBody String body) throws Exception {

		System.out.println(body.toString());

		// TODO: create umfrage.json file

		return "adfc/survey_list";

	}

}
