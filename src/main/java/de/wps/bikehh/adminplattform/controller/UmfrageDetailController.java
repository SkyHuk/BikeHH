package de.wps.bikehh.adminplattform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.adminplattform.service.UmfragenService;

@Controller
@RequestMapping("umfragen/{umfrageId}")
public class UmfrageDetailController {

	@Autowired
	UmfragenService umfrageService;

	/**
	 *
	 * Öffnet eine html-Seite für eine bestimmte Umfrage
	 *
	 * @param model     spring model
	 * @param umfrageId id der Umfrage, welche geöffnet werden soll
	 * @return html Seite
	 */
	@GetMapping
	public String zeigeEinzelUmfrage(Model model, @PathVariable Integer umfrageId) {

		Umfrage umfrage = umfrageService.getUmfrageNachId(umfrageId);
		model.addAttribute("umfrage", umfrage);

		return "adfc/umfrage";
	}
}
