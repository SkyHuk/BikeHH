package de.wps.bikehh.adminplattform.controller;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.benutzerverwaltung.material.Benutzer;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfrage-erstellen")
public class UmfrageErstellenController {

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
		model.addAttribute("benutzer", benutzer);
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

		Umfrage umfrage = Utils.getUmfrageNachId(umfrageId);
		Gson gson = new Gson();
		String jsonUmfrage = gson.toJson(umfrage);
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
	public String speichereUmfrage(@RequestBody String jsonString) {

		// old method
		// Utils.saveJSONSurveyInFilesOld(body);

		// new method
		// create umfrage.json file and validate jsonString
		try {
			Utils.speichereJSONStringImSpeicher(jsonString);
		} catch (IllegalArgumentException | ParseException e) {
			// TODO test
			System.out.println("IllegalArgumentException oder ParseException wurde in CreateSurveyController geworfen");
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "JSONString ist ungültig", e);
		}

		return "adfc/umfragen_liste";
	}

}
