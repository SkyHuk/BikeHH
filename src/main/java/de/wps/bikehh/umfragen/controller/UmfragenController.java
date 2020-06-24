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

@Controller
@RequestMapping("umfragen")
public class UmfragenController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;

	}

	@GetMapping
	public String zeigeUmfragenListe(Model model) {

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();

		model.addAttribute("umfragen", umfragen);
		return "adfc/umfragen_liste";
	}

	/**
	 *
	 * Öffnet eine html-Seite für eine bestimmte Umfrage
	 *
	 * @param model     spring model
	 * @param umfrageId id der Umfrage, welche geöffnet werden soll
	 * @return html Seite
	 */
	@GetMapping("/{umfrageId}")
	public String zeigeEinzelUmfrage(Model model, @PathVariable Integer umfrageId) {

		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);
		model.addAttribute("umfrage", umfrage);

		return "adfc/umfrage";
	}
}
