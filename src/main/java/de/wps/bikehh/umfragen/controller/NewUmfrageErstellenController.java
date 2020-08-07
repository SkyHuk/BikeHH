package de.wps.bikehh.umfragen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;
import de.wps.bikehh.umfragen.dto.FrageDto;
import de.wps.bikehh.umfragen.dto.NewUmfrageDto;

@Controller
@RequestMapping("umfragen/new")
public class NewUmfrageErstellenController {

	private UmfragenApplicationService umfragenAppService;

	@Autowired
	public NewUmfrageErstellenController(UmfragenApplicationService umfragenAppService) {
		this.umfragenAppService = umfragenAppService;
	}

	@GetMapping
	public String getNewUmfrageForEdit(Model model) {
		model.addAttribute("umfrage", new NewUmfrageDto());
		model.addAttribute("formPostUrl", "/umfragen/new");
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addFrage" })
	public String addFrage(@ModelAttribute("umfrage") final NewUmfrageDto umfrage, final BindingResult bindingResult) {
		umfrage.getFragen().add(new FrageDto());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeFrage" })
	public String removeFrage(
			@ModelAttribute("umfrage") final NewUmfrageDto umfrage, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer fragenIndex = Integer.valueOf(req.getParameter("removeFrage"));
		umfrage.getFragen().remove(fragenIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addAntwort" })
	public String addAntwort(@ModelAttribute("umfrage") final NewUmfrageDto umfrage, final BindingResult bindingResult,
			final HttpServletRequest req) {
		final Integer fragenIndex = Integer.valueOf(req.getParameter("addAntwort"));
		umfrage.getFragen().get(fragenIndex.intValue()).getAntworten().add("");
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeAntwort" })
	public String removeAntwort(@ModelAttribute("umfrage") NewUmfrageDto umfrage,
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

	@PostMapping
	public String postNewUmfrage(
			@ModelAttribute("user") User user,
			@ModelAttribute("umfrage") @Valid NewUmfrageDto umfrageDto,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			// Fehler beim Validieren durch Annotations im Dto
			return "umfragen/umfrage-form";
		}
		// TODO: fachliche Fehler prüfen.

		long umfrageId = umfragenAppService.addNewUmfrage(user, umfrageDto, true);
		return "redirect:/umfragen/" + umfrageId + "?saved";
	}
}
