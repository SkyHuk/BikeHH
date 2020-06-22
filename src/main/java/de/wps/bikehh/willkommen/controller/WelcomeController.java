package de.wps.bikehh.willkommen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.benutzerverwaltung.material.Benutzer;
import de.wps.bikehh.willkommen.material.Bier;

@Controller
@RequestMapping("welcome")
public class WelcomeController {

	@GetMapping
	public String showWelcome(@ModelAttribute("bikehh_user") Benutzer user, Model model) {

		List<Bier> bierliste = new ArrayList<>();
		bierliste.add(new Bier("Ratsherrn", "Pale Ale", 0.3));
		bierliste.add(new Bier("Ratsherrn", "Pils", 0.3));
		bierliste.add(new Bier("Ratsherrn", "White IPA", 0.5));
		bierliste.add(new Bier("Ratsherrn", "Matrosenschluck", 0.3));

		model.addAttribute("biere", bierliste);
		model.addAttribute("user", user);
		return "welcome/welcome";
	}
}
