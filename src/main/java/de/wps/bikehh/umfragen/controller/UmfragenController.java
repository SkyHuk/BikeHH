package de.wps.bikehh.umfragen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * Controller für alle Requests für "/umfragen/
 */
@Controller
@RequestMapping("umfragen")
public class UmfragenController {

	private UmfragenService umfragenService;

	/**
	 * constructor für JUNIT tests
	 * 
	 * @param umfragenService db service
	 */
	@Autowired
	public UmfragenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;

	}

	/**
	 * gibt das Template für die Umfragen-Liste
	 * 
	 * füllt das model mit allen existierenden Umfragen
	 * 
	 * @param model spring model
	 * @return HTML-Template
	 */
	@GetMapping
	public String zeigeUmfragenListe(Model model) {

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();

		model.addAttribute("umfragen", umfragen);
		return "umfragen/umfragen_liste";
	}

	/**
	 *
	 * Zeigt eine Einzelansicht einer Umfrage
	 * 
	 * Über int in URL (/umfragen/<umfrageId>) wird die anzuzeigenden Umfrage
	 * ermittelt
	 *
	 * @param model     spring model
	 * @param umfrageId id der Umfrage, welche geöffnet werden soll
	 * @return html Seite
	 */
	@GetMapping("/{umfrageId}")
	public String zeigeEinzelUmfrage(Model model, @PathVariable Integer umfrageId) {

		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);
		model.addAttribute("umfrage", umfrage);

		return "umfragen/umfrage";
	}

}
