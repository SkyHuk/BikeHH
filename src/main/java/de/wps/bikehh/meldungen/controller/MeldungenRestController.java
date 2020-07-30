package de.wps.bikehh.meldungen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Der MeldungenRestController ist die API Schnittstelle von mobile Anwendungen
 * zum Backend, um Meldungen zu senden.
 * 
 * TODO: Hinter "/api/"-authentifizierung schieben, um sicher zu machen (siehe
 * config.SecurityConfig.java)
 * 
 * @author felixwolf
 */
@Controller
@RequestMapping("/meldung-erstellen")
public class MeldungenRestController {

	/**
	 * Die Meldungen, die hier ankommen sollen in der Datenbank gespeichert
	 * werden.
	 * 
	 * Des Weiteren soll aus dieser Meldung eine Umfrage generiert werden, die
	 * auch in der Datenbank gespeichert wird
	 * 
	 * @param body
	 *            der body in JSON format, in dem sich die Meldung befindet
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void postMeldung(@RequestBody String body) {
		// TODO: speicher die Meldung in DB
		// TODO: generiere Umfrage aus Meldung
	}
}
