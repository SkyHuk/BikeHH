package de.wps.bikehh.umfragen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

@Controller
@RequestMapping("umfragen")
public class UmfragenController {

	@Autowired
	UmfragenService umfragenService;

	@GetMapping
	public String zeigeUmfragenListe(Model model) {

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();

		model.addAttribute("umfragen", umfragen);
		return "adfc/umfragen_liste";
	}
}
