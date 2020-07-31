package de.wps.bikehh.uebersicht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller für das Home-Verzeichnis "/".
 * 
 * @author felixwolf
 */
@Controller
@RequestMapping("/")
public class UebersichtController {

	@GetMapping
	public String home() {
		return "redirect:/uebersicht";
	}

	/**
	 * Gibt das HTML-Template für die Übersicht zurück
	 * 
	 * TODO: template ist leer, ausbauen. Mögliche Ideen: Statistiken über
	 * Nutzer, Umfragen und Meldungen
	 * 
	 * @return Das Template
	 */
	@GetMapping("uebersicht")
	public String zeigeUebersicht() {
		return "uebersicht/uebersicht";
	}
}
