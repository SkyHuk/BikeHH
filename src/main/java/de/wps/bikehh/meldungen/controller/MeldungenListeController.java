package de.wps.bikehh.meldungen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("meldungen")
public class MeldungenListeController {

	@GetMapping
	public String zeigeMeldungenListe() {

		return "adfc/meldungen_liste";
	}
}
