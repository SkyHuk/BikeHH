package de.wps.bikehh.befragungen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/{befragungId}")
	public String getBefragung(@PathVariable long befragungId, Model model) {
		if (!befragungenAppService.exists(befragungId)) {
			// Befragung existiert nicht. vllt Fehler in der Notificationbar
			// anzeigen.
			return "redirect:/befragungen";
		}
		model.addAttribute("befragung", befragungenAppService.getBefragung(befragungId));
		return "befragungen/befragung";
	}

}
