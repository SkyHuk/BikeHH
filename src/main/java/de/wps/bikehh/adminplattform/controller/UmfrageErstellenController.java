package de.wps.bikehh.adminplattform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.adminplattform.service.UmfragenService;
import de.wps.bikehh.benutzerverwaltung.material.Benutzer;

@Controller
@RequestMapping("umfrage-erstellen")
public class UmfrageErstellenController {

	@Autowired
	UmfragenService umfragenService;

	/**
	 * Öffnet umfrage_erstellen.html mit optional Koordinaten. Koordinaten werden
	 * auf 0 gesetzt, wenn sie nicht vorhanden sind.
	 *
	 * @param koordinaten coordinates if coming from map.html
	 * @param model       spring model
	 * @return html page
	 */
	@GetMapping
	public String zeigeUmfragenErsteller(@ModelAttribute("bikehh_user") Benutzer benutzer,
			@RequestParam(required = false, name = "koordinaten") double[] koordinaten, Model model) {
		if (koordinaten != null) {
			model.addAttribute("laengengrad", koordinaten[0]);
			model.addAttribute("breitengrad", koordinaten[1]);
		} else {
			model.addAttribute("breitengrad", 0);
			model.addAttribute("laengengrad", 0);
		}
		String jsonBenutzerString = new Gson().toJson(benutzer);
		model.addAttribute("benutzer", jsonBenutzerString);
		return "adfc/umfrage_erstellen";
	}

	/**
	 * 
	 * @param umfrageId
	 * @param model
	 * @return
	 */
	@GetMapping("/bearbeiten")
	public String zeigeUmfragenBearbeiter(@RequestParam(required = true, name = "umfrageId") int umfrageId,
			Model model) {

		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);

		String jsonUmfrage = new Gson().toJson(umfrage);
		model.addAttribute("umfrage", jsonUmfrage);
		return "adfc/umfrage_erstellen";
	}

	/**
	 * Speichert den JSON-String im Speicher
	 *
	 * @param jsonString post body
	 * @return html page
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PostMapping("/umfragen")
	@ResponseBody
	public int speichereUmfrage(@RequestBody String jsonString) {

		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		return umfragenService.speichereOderUpdateUmfrage(umfrage);
	}

}
