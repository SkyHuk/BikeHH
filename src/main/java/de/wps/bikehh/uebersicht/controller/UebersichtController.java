package de.wps.bikehh.uebersicht.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UebersichtController {

	@GetMapping
	public String home() {
		return "redirect:/uebersicht";
	}

	@GetMapping("uebersicht")
	public String zeigeUebersicht() {
		return "adfc/uebersicht";
	}
}
