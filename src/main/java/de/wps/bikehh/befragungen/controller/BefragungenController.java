package de.wps.bikehh.befragungen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.befragungen.applicationservice.BefragungenApplicationService;

@Controller
@RequestMapping("/befragungen")
public class BefragungenController {

	private BefragungenApplicationService befragungenAppService;

	@Autowired
	public BefragungenController(BefragungenApplicationService befragungenAppService) {
		this.befragungenAppService = befragungenAppService;
	}

	@GetMapping
	public String getBefragungenListe(Model model) {
		model.addAttribute("befragungen", befragungenAppService.getAlleBefragungen());
		return "befragungen/befragungen_liste";
	}
}
