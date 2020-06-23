package de.wps.bikehh.adminplattform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MeldungController {

	@RequestMapping(method = RequestMethod.POST)
	public void speichereMeldung(@RequestBody String jsonString) {
		// speicher die Meldung in DB / als JSON lul
	}
}
