package de.wps.bikehh.umfragen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * Controller für alle Requests für "/umfragen
 */
@Controller
@RequestMapping("umfragen")
public class UmfragenController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * Gibt das Template für die Umfragen-Liste und füllt das model mit allen
	 * existierenden Umfragen.
	 *
	 * @param model
	 *            spring model
	 */
	@GetMapping
	public String zeigeUmfragenListe(Model model) {
		model.addAttribute("umfragen", umfragenService.getAlleUmfragen());
		return "umfragen/umfragen_liste";
	}

	/**
	 * Zeigt eine Einzelansicht einer Umfrage.
	 *
	 * Über URL-Parameter (/umfragen/<umfrageId>) wird die anzuzeigende Umfrage
	 * ermittelt
	 *
	 * @param model
	 *            spring model
	 * @param umfrageId
	 *            id der Umfrage, welche geöffnet werden soll
	 */
	@GetMapping("/{umfrageId}")
	public String zeigeEinzelUmfrage(Model model, @PathVariable Integer umfrageId) {
		Umfrage umfrage = umfragenService.getById(umfrageId);
		model.addAttribute("umfrage", umfrage);

		// TODO jg: Inspektion was das soll
		String umfrageAlsJsonString = new Gson().toJson(umfrage);
		model.addAttribute("umfrageJSON", umfrageAlsJsonString);
		return "umfragen/umfrage";
	}

	/**
	 * Deaktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PatchMapping("/disable/{umfrageId}")
	public String deaktiviereUmfrage(@PathVariable int umfrageId) {
		Umfrage umfrage = umfragenService.getById(umfrageId);
		umfrage.setUmfrageDisabled(true);
		umfragenService.save(umfrage);

		return "redirect:/umfrage/" + umfrage.getId();
	}

	/**
	 * Aktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PatchMapping("/enable/{umfrageId}")
	public String aktiviereUmfrage(@PathVariable int umfrageId) {
		Umfrage umfrage = umfragenService.getById(umfrageId);
		umfrage.setUmfrageDisabled(false);
		umfragenService.save(umfrage);

		return "redirect:/umfrage/" + umfrage.getId();
	}

}
