package de.wps.bikehh.kategorien.controller;

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

import de.wps.bikehh.kategorien.applicationservice.KategorienApplicationService;
import de.wps.bikehh.kategorien.dto.NewOberkategorieDto;
import de.wps.bikehh.kategorien.dto.NewUnterkategorieDto;

@Controller
@RequestMapping("kategorien")
public class KategorienController {

	private KategorienApplicationService kategorienAppService;

	@Autowired
	public KategorienController(KategorienApplicationService kategorienAppService) {
		this.kategorienAppService = kategorienAppService;
	}

	@GetMapping
	public String getKategorienListe(Model model) {
		addCommonAttributes(model);
		return "kategorien/kategorien_liste";
	}

	@PostMapping
	public String postNewOberkategorie(
			@ModelAttribute("kategorie") @Valid NewOberkategorieDto kategorie,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "kategorien/kategorien_liste";
		}

		kategorienAppService.addNewOberkategorie(kategorie.getName());
		return "redirect:/kategorien";
	}

	@GetMapping("/{kategorieId}/delete")
	public String deleteKategorie(@PathVariable long kategorieId) {
		if (!kategorienAppService.exists(kategorieId)) {
			// TODO: Kategorie existiert nicht, Fehler anzeigen.
			// Notificationbar?
			return "kategorien/kategorien_liste";
		}

		kategorienAppService.delete(kategorieId);
		return "redirect:/kategorien";
	}

	@GetMapping("/{kategorieId}/add")
	public String getViewToAddNewUnterkategorie(@PathVariable long kategorieId, Model model) {
		if (!kategorienAppService.exists(kategorieId)) {
			// TODO: Kategorie existiert nicht, Fehler anzeigen.
			// Notificationbar?
			return "kategorien/kategorien_liste";
		}

		model.addAttribute("unterKategorie", kategorienAppService.createUnterkategorieDto(kategorieId));
		addCommonAttributes(model);
		return "kategorien/kategorien_liste";
	}

	@PostMapping("/{kategorieId}/add")
	public String postNewUnterkategorie(@PathVariable long kategorieId,
			@ModelAttribute("unterKategorie") @Valid NewUnterkategorieDto unterKategorie,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "kategorien/kategorien_liste";
		}

		kategorienAppService.addNewUnterKategorie(unterKategorie);
		return "redirect:/kategorien";
	}

	private void addCommonAttributes(Model model) {
		model.addAttribute("kategorien", kategorienAppService.getAlleKategorien());
		model.addAttribute("kategorie", new NewOberkategorieDto());
	}

}
