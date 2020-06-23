package de.wps.bikehh.adminplattform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("uebersicht")
public class UebersichtController {

	@GetMapping
	public String zeigeUebersicht() {
		return "adfc/uebersicht";
	}
}
