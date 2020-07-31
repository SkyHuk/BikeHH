package de.wps.bikehh.umfragen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * API Schnittstelle für Umfragen
 */
@RestController
@RequestMapping("api/umfragen")
public class UmfragenRestController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenRestController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	/**
	 * Löscht die Umfrage, die zu der übergebenen id im URL-Parameter gehört.
	 */
	@DeleteMapping(value = "/delete/{umfrageId}")
	public void loescheUmfrage(@PathVariable int umfrageId) {
		umfragenService.loesche(umfrageId);
	}

	/**
	 * Deaktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PatchMapping("/disable/{umfrageId}")
	public void deaktiviereUmfrage(@PathVariable int umfrageId) {
		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);
		umfrage.setUmfrageDisabled(true);
		umfragenService.speichereOderUpdateUmfrage(umfrage);
	}

	/**
	 * Aktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PatchMapping("/enable/{umfrageId}")
	public void aktiviereUmfrage(@PathVariable int umfrageId) {
		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);
		umfrage.setUmfrageDisabled(false);
		umfragenService.speichereOderUpdateUmfrage(umfrage);
	}

}
