package de.wps.bikehh.meldungen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.meldungen.applicationservice.MeldungenApplicationService;

/**
 * Dieser Controller behandelt alle Anfragen auf "/meldungen"
 */
@Controller
@RequestMapping("meldungen")
public class MeldungenController {

	private MeldungenApplicationService meldungenAppService;

	@Autowired
	public MeldungenController(MeldungenApplicationService meldungenAppService) {
		this.meldungenAppService = meldungenAppService;
	}

	@GetMapping
	public String getMeldungenUbersicht(Model model) {
		model.addAttribute("meldungen", meldungenAppService.getAlleMeldungen());
		return "meldungen/meldungen_liste";
	}
}
