package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("survey") // TODO: WAS KOMMT HIER HIN?
public class SingleSurveyController {

	@GetMapping
	public String showSingleSurvey(Model model) {

		model.addAttribute("UMFRAGEDETAILS");
		return "adfc/survey";
	}
}
