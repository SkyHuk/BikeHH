package de.wps.bikehh.umfragen.controller;

import javax.servlet.http.HttpServletRequest;
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
import de.wps.bikehh.umfragen.dto.FrageDto;

@Controller
@RequestMapping("umfragen/{umfrageId}/edit")
public class EditUmfrageController {

	private UmfragenApplicationService umfragenAppService;

	@Autowired
	public EditUmfrageController(UmfragenApplicationService umfragenAppService) {
		this.umfragenAppService = umfragenAppService;
	}

	@GetMapping
	public String getUmfrageForEdit(Model model, @PathVariable long umfrageId) {
		if (!umfragenAppService.hasUmfrage(umfrageId)) {
			// TODO: Hat Umfrage nicht. Fehler anzeigen.
		}
		model.addAttribute("umfrage", umfragenAppService.getUmfrageForEdit(umfrageId));
		model.addAttribute("formPostUrl", "/umfragen/" + umfrageId + "/edit");
		model.addAttribute("umfrageId", umfrageId);
		return "umfragen/umfrage-form";
	}

	@PostMapping
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
		return "redirect:/umfragen/" + umfrageId + "?saved";
	}

	@RequestMapping(params = { "addFrage" })
	public String addFrage(@ModelAttribute("umfrage") EditUmfrageDto umfrage, BindingResult bindingResult) {
		umfrage.getFragen().add(new FrageDto());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeFrage" })
	public String removeFrage(
			@ModelAttribute("umfrage") EditUmfrageDto umfrage, BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer fragenIndex = Integer.valueOf(req.getParameter("removeFrage"));
		umfrage.getFragen().remove(fragenIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addAntwort" })
	public String addAntwort(@ModelAttribute("umfrage") EditUmfrageDto umfrage, BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer fragenIndex = Integer.valueOf(req.getParameter("addAntwort"));
		umfrage.getFragen().get(fragenIndex.intValue()).getAntworten().add("");
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeAntwort" })
	public String removeAntwort(@ModelAttribute("umfrage") EditUmfrageDto umfrage,
			BindingResult bindingresult,
			HttpServletRequest req) {
		// Da wir einen doppelten Parameter übergeben müssen um einmal die Frage
		// zu bestimmen und einmal die Antwort muss es gepaired als ein Wert
		// übergeben werden
		// TODO: Die letzte Antwort wird immer nicht entfernt. Warum auch immer.
		String frageAntwortPair = req.getParameter("removeAntwort");
		Integer fragenIndex = Integer.valueOf(frageAntwortPair.split(";")[0]);
		Integer antwortIndex = Integer.valueOf(frageAntwortPair.split(";")[1]);
		umfrage.getFragen().get(fragenIndex.intValue()).getAntworten().remove(antwortIndex.intValue());
		return "umfragen/umfrage-form";
	}

}
