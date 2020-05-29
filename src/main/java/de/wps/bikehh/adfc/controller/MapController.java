package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("karte")
public class MapController {

	@GetMapping
	public String showMap() {
		return "adfc/map";
	}

}
