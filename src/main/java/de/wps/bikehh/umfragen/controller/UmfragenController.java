package de.wps.bikehh.umfragen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;

@Controller
@RequestMapping("umfragen")
public class UmfragenController {

	private UmfragenApplicationService umfragenAppService;

	@Autowired
	public UmfragenController(UmfragenApplicationService umfragenAppService) {
		this.umfragenAppService = umfragenAppService;
	}

	/**
	 * Gibt das Template für die Umfragen-Liste und füllt das model mit allen
	 * existierenden Umfragen.
	 */
	@GetMapping
	public String zeigeUmfragenListe(Model model) {
		// TODO: Pagination
		model.addAttribute("umfragen", umfragenAppService.getUmfragenUebersichtsListe());
		return "umfragen/umfragen_liste";
	}

	/**
	 * Zeigt eine Einzelansicht einer Umfrage mit gegebener Id.
	 *
	 * @param umfrageId
	 *            id der Umfrage, welche geöffnet werden soll
	 */
	@GetMapping("/{umfrageId}")
	public String zeigeEinzelUmfrage(Model model, @PathVariable long umfrageId) {
		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			// TODO: Hat Umfrage nicht. Fehler anzeigen.
		}
		model.addAttribute("umfrage", umfragenAppService.getUmfrageById(umfrageId));
		return "umfragen/umfrage";
	}

	/**
	 * Deaktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@GetMapping("/{umfrageId}/disable")
	public String deaktiviereUmfrage(@PathVariable long umfrageId) {
		if (umfragenAppService.hasUmfrage(umfrageId)) {
			umfragenAppService.disableUmfrage(umfrageId);
		}

		return "redirect:/umfragen/" + umfrageId;
	}

	/**
	 * Aktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@GetMapping("/{umfrageId}/enable")
	public String aktiviereUmfrage(@PathVariable long umfrageId) {
		if (umfragenAppService.hasUmfrage(umfrageId)) {
			umfragenAppService.enableUmfrage(umfrageId);
		}

		return "redirect:/umfragen/" + umfrageId;
	}

}
