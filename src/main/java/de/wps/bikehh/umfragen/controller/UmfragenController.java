package de.wps.bikehh.umfragen.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;
import de.wps.bikehh.umfragen.dto.EditUmfrageDto;
import de.wps.bikehh.umfragen.dto.NewUmfrageDto;

/**
 * Controller für alle Requests für "/umfragen
 */
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

	@GetMapping("/new")
	public String getNewUmfrageForEdit(Model model) {
		model.addAttribute("umfrage", new NewUmfrageDto());
		model.addAttribute("formPostUrl", "/umfragen/new");
		return "umfragen/umfrage-form";
	}

	@PostMapping("/new")
	public String postNewUmfrage(
			@ModelAttribute("umfrage") @Valid NewUmfrageDto umfrageDto,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			// Fehler beim Validieren durch Annotations im Dto
			return "umfragen/umfrage-form";
		}
		// TODO: fachliche Fehler prüfen.

		long umfrageId = umfragenAppService.addNewUmfrage(umfrageDto);
		return "redirect:/umfragen/" + umfrageId + "?saved";
	}

	@GetMapping("/{umfrageId}/edit")
	public String getUmfrageForEdit(Model model, @PathVariable long umfrageId) {
		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			// TODO: Hat Umfrage nicht. Fehler anzeigen.
		}
		model.addAttribute("umfrage", umfragenAppService.getUmfrageForEdit(umfrageId));
		model.addAttribute("formPostUrl", "/umfragen/" + umfrageId);
		model.addAttribute("umfrageId", umfrageId);
		return "umfragen/umfrage-form";
	}

	@PostMapping("/{umfrageId}")
	public String postEditedUmfrage(@PathVariable long umfrageId,
			@ModelAttribute("umfrage") @Valid EditUmfrageDto umfrageDto,
			BindingResult bindingResult,
			Model model) {

		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			bindingResult.reject("umfrage.does.not.exist");
		}

		if (bindingResult.hasErrors()) {
			// Fehler beim Validieren durch Annotations im Dto
			return "umfragen/umfrage-form";
		}
		// TODO: fachliche Fehler prüfen.

		umfragenAppService.saveEditedUmfrage(umfrageDto);
		return "redirect:/umfragen/" + umfrageId;
	}

	/**
	 * Deaktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PostMapping("/disable/{umfrageId}")
	public String deaktiviereUmfrage(BindingResult bindingResult, @PathVariable long umfrageId) {
		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			bindingResult.reject("umfrage.does.not.exist");
		}

		if (bindingResult.hasErrors()) {
			return "umfragen/umfrage";
		}

		umfragenAppService.disableUmfrage(umfrageId);

		return "redirect:/umfragen/" + umfrageId;
	}

	/**
	 * Aktiviert eine Umfrage zur gegebenen UmfrageId im URL-Parameter.
	 */
	@PostMapping("/enable/{umfrageId}")
	public String aktiviereUmfrage(BindingResult bindingResult, @PathVariable long umfrageId) {
		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			bindingResult.reject("umfrage.does.not.exist");
		}

		if (bindingResult.hasErrors()) {
			return "umfragen/umfrage";
		}

		umfragenAppService.enableUmfrage(umfrageId);

		return "redirect:/umfragen/" + umfrageId;
	}

}
