package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.utilities.Utils;

@Controller
@RequestMapping("karte")
public class KartenController {

	@GetMapping
	public String zeigeKarte() {
		return "adfc/karte";
	}

	@ModelAttribute("umfragen")
	public List<Umfrage> umfragen() {
		System.out.print("umfragen wurde aufgerufen\n");

		List<Umfrage> umfragen = Utils.getUmfragenAusSpeicher();

		return umfragen;
	}

}
