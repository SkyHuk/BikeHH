package de.wps.bikehh.umfragen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

/**
 * Rest Controller für Umfragen
 *
 */

@Controller
@RequestMapping("umfragen")
public class UmfragenRestController {

	private UmfragenService umfragenService;

	/**
	 * constructor für JUNIT tests
	 * 
	 * @param umfragenService db service
	 */
	@Autowired
	public UmfragenRestController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;

	}

	/**
	 * Löscht die Umfrage, die zu der id in der URL gehört, gibt danach das Template
	 * der Umfrage-List zurück
	 * 
	 * Webseite muss trotzdem neu geladen werden, um Änderungen darzustellen, warum
	 * auch immer. Reload im javascript
	 * 
	 * @param model     spring model
	 * @param umfrageId id der zu löschenden Umfrage
	 * @return Umfragen-Liste HTML-Template
	 */
	@RequestMapping(value = "/delete/{umfrageId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String loescheUmfrage(Model model, @PathVariable int umfrageId) {
		umfragenService.loesche(umfrageId);

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();
		model.addAttribute("umfragen", umfragen);
		return "adfc/umfragen_liste";
	}

	/**
	 * Deaktivert oder aktiviert eine Umfrage, die zu der id in der URL gehört, gibt
	 * danach die Einzel-Umfrage zurück
	 * 
	 * Wenn eine Umfrage aktiviert ist, wird sie deaktiviert. Ansonsten andersherum
	 * genauso.
	 * 
	 * @param model     spring model
	 * @param umfrageId id der zu deaktivierende/aktivierende Umfrage
	 * @return Einzelumfrage HTML-Template
	 */
	@RequestMapping(value = "/disable/{umfrageId}", method = RequestMethod.PATCH)
	@ResponseBody
	public String deaktiviereUmfrage(Model model, @PathVariable int umfrageId) {
		Umfrage umfrage = umfragenService.getUmfrageNachId(umfrageId);

		if (umfrage.isUmfrageDisabled()) {
			umfrage.setUmfrageDisabled(false);
		} else {
			umfrage.setUmfrageDisabled(true);
		}
		umfragenService.speichereOderUpdateUmfrage(umfrage);

		model.addAttribute("umfrage", umfrage);

		return "umfragen/umfrage";
	}
}
