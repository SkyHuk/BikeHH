package de.wps.bikehh.umfragenerstellen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.util.Validator;

/**
 * RestController für das Erstellen und Bearbeiten von Umfragen.
 */
@RestController
@RequestMapping("api/umfrage-erstellen")
public class UmfrageErstellenRestController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfrageErstellenRestController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * Speichert die Umfrage in der Datenbank.
	 *
	 * @param body
	 *            der post body, in dem sich die Umfrage in JSON Format befindet
	 * @return die id der erstellten Umfrage
	 * @throws Exception
	 *             wenn die Umfrage nicht dem geforderten Format entspricht
	 *             (wird in Utils überprüft), wird eine Exception geworfen
	 */
	@PostMapping("/umfragen-erstellen")
	@ResponseBody
	public long postUmfrage(@RequestBody Umfrage umfrage) {

		// validiere Umfrage
		if (Validator.umfrageIstValide(umfrage)) {
			return umfragenService.save(umfrage);

		} else {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Umfrage ist nicht valide und wird nicht gespeichert");
		}
	}

	/**
	 * updatet eine bereits bestehende Umfrage
	 *
	 * @param body
	 *            der Patch body, in dem sich die Umfrage in JSON Format
	 *            befindet
	 * @return die Id der geupdateten Umfrage
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	@PostMapping("/umfragen-erstellen")
	@ResponseBody
	public long updateUmfrage(@RequestBody Umfrage umfrage) {

		// validiere Umfrage
		if (Validator.umfrageIstValide(umfrage)) {

			long neueUmfrage = umfragenService.save(umfrage);
			// eine alte Umfrage wurde bearbeitet, muss geupdated werden
			Umfrage oldUmfrage = umfragenService.getById(neueUmfrage);
			oldUmfrage.merge(umfrage);

			return oldUmfrage.getId();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"Umfrage ist nicht valide und wird nicht gespeichert");
		}
	}
}
