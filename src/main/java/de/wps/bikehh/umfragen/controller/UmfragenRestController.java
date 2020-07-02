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

@Controller
@RequestMapping("umfragen")
public class UmfragenRestController {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenRestController(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;

	}

	/**
	 * Löscht eine Umfrage und zeigt danach alle Umfragen
	 * 
	 * @param model
	 * @param umfrageId
	 * @return
	 */
	@RequestMapping(value = "/delete/{umfrageId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String loescheUmfrage(Model model, @PathVariable int umfrageId) {
		umfragenService.loesche(umfrageId);

		List<Umfrage> umfragen = umfragenService.getAlleUmfragen();
		System.out.println(umfragen.size());
		model.addAttribute("umfragen", umfragen);
		return "adfc/umfragen_liste";
	}
}
