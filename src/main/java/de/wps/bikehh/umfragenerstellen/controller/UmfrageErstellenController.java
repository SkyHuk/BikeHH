package de.wps.bikehh.umfragenerstellen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * spring controller für alle requests auf "/umfrage-erstllen"
 *
 *
 */
@Controller
@RequestMapping("umfrage-erstellen")
public class UmfrageErstellenController {

	private UmfragenService umfragenService;

	/**
	 * Constructor für JUNIT Tests
	 *
	 * @param umfragenService
	 */
	@Autowired
	public UmfrageErstellenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * Öffnet umfrage_erstellen.html mit optional Koordinaten um eine Umfrage neu zu
	 * erstellen. Koordinaten werden auf 0 gesetzt, wenn sie nicht vorhanden sind.
	 *
	 * @param koordinaten koordinaten für die zu erstellende Umfrage. sind dann
	 *                    gesetzt, wenn über die Kartenfunktion eine umfrage
	 *                    erstellt wird
	 * @param user        der momentan eingeloggte User (Admin), kommt aus der
	 *                    Datenbank. TODO: statt vollständigem User (security leak,
	 *                    passwort-hash in klartext sichtbar) dto mit benötigten
	 *                    inhalten (Email) weitergeben. Anschließend anhand DTO User
	 *                    aus Datenbank ziehen und vollständig in Umfrage schreiben,
	 *                    damit diese vollständig gespeichert werden kann
	 * @param model       spring model
	 * @return HTML-TEMPLATE für umfrage-erstellen
	 */
	@GetMapping
	public String zeigeUmfragenErsteller(@ModelAttribute("user") User user,
			@RequestParam(required = false, name = "koordinaten") double[] koordinaten, Model model) {
		if (koordinaten != null) {
			model.addAttribute("laengengrad", koordinaten[0]);
			model.addAttribute("breitengrad", koordinaten[1]);
		} else {
			model.addAttribute("breitengrad", 0);
			model.addAttribute("laengengrad", 0);
		}
		String jsonBenutzerString = new Gson().toJson(user);
		model.addAttribute("benutzer", jsonBenutzerString);
		return "umfrageerstellen/umfrage_erstellen";
	}

	/**
	 *
	 * öffnet die gleiche seite / liefert das gleiche HTML-Template wie
	 * zeigeUmfrageErsteller(..), hat aber andere Parameter, die darauf ausgelegt
	 * sind, eine bestehende Umfrage zu bearbeiten
	 *
	 * @param umfrageId die id der umfrage, die bearbeitet werden soll
	 * @param model     spring model
	 * @return HTML-Template
	 */
	@GetMapping("/bearbeiten")
	public String zeigeUmfragenBearbeiter(@RequestParam(required = true, name = "umfrageId") int umfrageId,
			Model model) {

		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);
		String jsonUmfrage = new Gson().toJson(umfrage);

		model.addAttribute("umfrage", jsonUmfrage);
		return "umfrageerstellen/umfrage_erstellen";
	}

}
