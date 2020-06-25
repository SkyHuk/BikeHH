package de.wps.bikehh.karte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

@Controller
@RequestMapping("karte")
public class KartenController {

	private UmfragenService umfragenService;

	@Autowired
	public KartenController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	@GetMapping
	public String zeigeKarte(Model model) {

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();

		model.addAttribute("umfragen", umfragen);
		return "adfc/karte";
	}

	/*
	 * @ModelAttribute("umfragen") public List<Umfrage> umfragen() {
	 * 
	 * List<Umfrage> umfragen = umfragenService.getAlleUmfragen();
	 * 
	 * return umfragen; }
	 */

}
