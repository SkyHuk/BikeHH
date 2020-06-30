package de.wps.bikehh.umfragenerstellen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.util.Utils;

@Controller
@RequestMapping("umfrage-erstellen")
public class UmfrageErstellenRestController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfrageErstellenRestController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * Speichert den JSON-String im Speicher
	 *
	 * @param jsonString post body
	 * @return html page
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PostMapping("/umfragen-erstellen")
	@ResponseBody
	public int speichereUmfrage(@RequestBody String jsonString) {

		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		System.out.println(jsonString);

		// validiere Umfrage
		if (Utils.umfrageIstValide(umfrage)) {
			if (umfrage.getId() == 0) {
				// die umfrage ist neu erstellt worden und nicht eine alte bearbeitete
				return umfragenService.speichereOderUpdateUmfrage(umfrage);
			}
			int neueUmfrage = umfragenService.speichereOderUpdateUmfrage(umfrage);
			// eine alte Umfrage wurde bearbeitet, muss geupdated werden
			Umfrage oldUmfrage = umfragenService.getUmfrageNachId(neueUmfrage);
			oldUmfrage.merge(umfrage);

			return oldUmfrage.getId();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Umfrage ist nicht valide und wird nicht gespeichert");
		}
	}
}
