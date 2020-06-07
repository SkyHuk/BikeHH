package de.wps.bikehh.adfc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adfc.material.SurveyTest;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("karte")
public class MapController {

	@GetMapping
	public String showMap() {
		return "adfc/map";
	}

	// test only
	@ModelAttribute("test")
	public List<String> testMethod() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello World");
		// test only
		// System.out.print("test was called");
		return list;
	}

	@ModelAttribute("surveys")
	public List<SurveyTest> surveys() {
		System.out.print("surveys was called\n");

		List<SurveyTest> surveys = Utils.getSurveyJsonsAsArray();

		return surveys;
	}

}
