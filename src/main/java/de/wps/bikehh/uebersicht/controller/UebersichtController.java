package de.wps.bikehh.uebersicht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UebersichtController {

	@GetMapping
	public String home() {
		return "redirect:/uebersicht";
	}

	/**
	 * Gibt das HTML-Template für die Übersicht zurück
	 */
	@GetMapping("uebersicht")
	public String zeigeUebersicht() {
		return "uebersicht/uebersicht";
	}
}
