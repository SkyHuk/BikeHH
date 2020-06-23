package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfragen/{umfrageId}")
public class UmfrageDetailController {

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

		List<Umfrage> umfragenListe = Utils.getUmfragenAusSpeicher();
		for (Umfrage umfrage : umfragenListe) {
			if (umfrage.getId() == umfrageId) {
				model.addAttribute("umfrage", umfrage);
			}
		}

		return "adfc/umfrage";
	}
}
