package de.wps.bikehh.uebersicht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller für das Home-Verzeichnis "/".
 * 
 * @author felixwolf
 *
 */
@Controller
@RequestMapping("/")
public class UebersichtController {

	// TODO: Diese Klasse muss getestet werden, als Beispiel siehe andere Controller

	/**
	 * redirected von "/" nach "/uebersicht"
	 * 
	 * @return der redirect
	 */
	@GetMapping
	public String home() {
		return "redirect:/uebersicht";
	}

	/**
	 * gibt das HTML-Template für die Übersicht zurück
	 * 
	 * TODO: template ist leer, ausbauen. Mögliche Ideen: Statistiken über Nutzer,
	 * Umfragen und Meldungen
	 * 
	 * @return das template
	 */
	@GetMapping("uebersicht")
	public String zeigeUebersicht() {
		return "uebersicht/uebersicht";
	}
}
