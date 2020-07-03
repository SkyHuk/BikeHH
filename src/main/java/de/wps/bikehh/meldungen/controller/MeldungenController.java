package de.wps.bikehh.meldungen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Dieser Controller behandelt alle Anfragen auf "/meldungen"
 */
@Controller
@RequestMapping("meldungen")
public class MeldungenController {

	// TODO: Klasse erstellen
	// private MeldungenService meldungenService;

	/*
	 * Constructor für JUNIT tests TODO: implementieren, Test schreiben
	 */
	// @Autowired
	// public MeldungenController(MeldungenService meldungenService) {
	// this.meldungenService = meldungenService
	// }

	/**
	 * zeigt eine Liste mit allen Meldungen
	 * 
	 * TODO: meldungen in das model laden
	 * 
	 * @return das HTML template für die Meldungen Liste
	 */
	@GetMapping
	public String zeigeMeldungenListe(Model model) {

		// TODO: funktion implementieren
		// List<Meldung> meldungenListeList = umfrageService.getAlleMeldungen()
		// model.addAttribute("meldungen", meldungen)

		return "meldungen/meldungen_liste";
	}
}
