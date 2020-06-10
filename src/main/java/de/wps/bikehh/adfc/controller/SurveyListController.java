package de.wps.bikehh.adfc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adfc.material.SurveyTest;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfragen")
public class SurveyListController {

	@GetMapping
	public String showSurveyList(Model model) {

		List<SurveyTest> surveys = Utils.getSurveyJsonsAsArray();

		System.out.println(surveys.get(0).getConfirmedByUsers());
		model.addAttribute("surveys", surveys);
		return "adfc/survey_list";
	}
}
