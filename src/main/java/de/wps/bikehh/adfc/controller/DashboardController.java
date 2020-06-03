package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("uebersicht")
public class DashboardController {

	@GetMapping
	public String showDashboard() {
		return "adfc/dashboard";
	}
}
