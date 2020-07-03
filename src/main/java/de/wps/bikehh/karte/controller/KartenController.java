package de.wps.bikehh.karte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * Dieser Controller ist der Anlaufpunkt für alle Requests auf "/karte"
 * 
 *
 */
@Controller
@RequestMapping("karte")
public class KartenController {

	private UmfragenService umfragenService;

	/**
	 * Benötigt für JUNIT tests
	 * 
	 * @param umfragenService der Service
	 */
	@Autowired
	public KartenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * gibt das HTML-Template für "/karte" zurück in das model werden alle Umfragen
	 * geladen, um sie auf der Karte anzuzeigen (mit den kleinem Markern, die
	 * Informationen zu der Umfrage beihalten
	 * 
	 * @param model springboot model mit allesn Umfragen
	 * @return das HTML-Template für Karte
	 */
	@GetMapping
	public String zeigeKarte(Model model) {

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();

		model.addAttribute("umfragen", umfragen);
		return "karte/karte";
	}
}
