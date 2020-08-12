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
import de.wps.bikehh.umfragen.dto.EditBefragungDto;
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

	@RequestMapping(params = { "addBefragung" })
	public String addBefragung(@ModelAttribute("umfrage") NewUmfrageDto umfrage) {
		EditBefragungDto dtoToAdd = new EditBefragungDto();
		dtoToAdd.getFragen().add(new FrageDto());
		umfrage.getBefragungen().add(dtoToAdd);
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeBefragung" })
	public String removeBefragung(
			@ModelAttribute("umfrage") NewUmfrageDto umfrage, HttpServletRequest req) {
		Integer befragungIndex = Integer.valueOf(req.getParameter("removeBefragung"));
		umfrage.getBefragungen().remove(befragungIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addFrage" })
	public String addFrage(@ModelAttribute("umfrage") NewUmfrageDto umfrage, HttpServletRequest req) {
		Integer befragungIndex = Integer.valueOf(req.getParameter("addFrage"));
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().add(new FrageDto());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeFrage" })
	public String removeFrage(
			@ModelAttribute("umfrage") NewUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("removeFrage");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().remove(fragenIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addAntwort" })
	public String addAntwort(@ModelAttribute("umfrage") NewUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("addAntwort");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().get(fragenIndex.intValue())
				.getAntworten().add("");
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeAntwort" })
	public String removeAntwort(@ModelAttribute("umfrage") NewUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("removeAntwort");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		Integer antwortIndex = Integer.valueOf(befragungFragePair.split(";")[2]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().get(fragenIndex.intValue())
				.getAntworten().remove(antwortIndex.intValue());
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
		// TODO: Fragen dürfen keine Semikolons enthalten und als konvertierte
		// Liste max 255 Zeichen haben

		long umfrageId = umfragenAppService.addNewUmfrage(user, umfrageDto);
		return "redirect:/umfragen/" + umfrageId + "?saved";
	}
}
