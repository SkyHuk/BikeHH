package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("umfragen")
public class UmfragenListeController {

	@GetMapping
	public String zeigeUmfragenListe(Model model) {

		List<Umfrage> umfragen = Utils.getUmfragenAusSpeicher();

		model.addAttribute("umfragen", umfragen);
		return "adfc/umfragen_liste";
	}
}
