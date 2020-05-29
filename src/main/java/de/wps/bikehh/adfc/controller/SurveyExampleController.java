package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("umfrage-beispiel")
public class SurveyExampleController {

	@GetMapping
	public String showSurveyExample() {
		return "adfc/survey-example";
	}
}
