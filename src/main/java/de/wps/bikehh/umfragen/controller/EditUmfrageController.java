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
import de.wps.bikehh.umfragen.dto.EditBefragungDto;
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

	@RequestMapping(params = { "addBefragung" })
	public String addBefragung(@ModelAttribute("umfrage") EditUmfrageDto umfrage) {
		EditBefragungDto dtoToAdd = new EditBefragungDto();
		dtoToAdd.getFragen().add(new FrageDto());
		umfrage.getBefragungen().add(dtoToAdd);
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeBefragung" })
	public String removeBefragung(
			@ModelAttribute("umfrage") EditUmfrageDto umfrage, HttpServletRequest req) {
		Integer befragungIndex = Integer.valueOf(req.getParameter("removeBefragung"));
		umfrage.getBefragungen().remove(befragungIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addFrage" })
	public String addFrage(@ModelAttribute("umfrage") EditUmfrageDto umfrage, HttpServletRequest req) {
		Integer befragungIndex = Integer.valueOf(req.getParameter("addFrage"));
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().add(new FrageDto());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeFrage" })
	public String removeFrage(
			@ModelAttribute("umfrage") EditUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("removeFrage");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().remove(fragenIndex.intValue());
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "addAntwort" })
	public String addAntwort(@ModelAttribute("umfrage") EditUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("addAntwort");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().get(fragenIndex.intValue())
				.getAntworten().add("");
		return "umfragen/umfrage-form";
	}

	@RequestMapping(params = { "removeAntwort" })
	public String removeAntwort(@ModelAttribute("umfrage") EditUmfrageDto umfrage, HttpServletRequest req) {
		String befragungFragePair = req.getParameter("removeAntwort");
		Integer befragungIndex = Integer.valueOf(befragungFragePair.split(";")[0]);
		Integer fragenIndex = Integer.valueOf(befragungFragePair.split(";")[1]);
		Integer antwortIndex = Integer.valueOf(befragungFragePair.split(";")[2]);
		umfrage.getBefragungen().get(befragungIndex.intValue())
				.getFragen().get(fragenIndex.intValue())
				.getAntworten().remove(antwortIndex.intValue());
		return "umfragen/umfrage-form";
	}

}
