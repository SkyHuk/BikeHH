package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("umfragen")
public class SurveyListController {

	@GetMapping
	public String showSurveyList() {

		return "adfc/survey_list";
	}
}
