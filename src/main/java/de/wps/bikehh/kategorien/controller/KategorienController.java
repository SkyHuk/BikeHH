package de.wps.bikehh.kategorien.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.kategorien.applicationservice.KategorienApplicationService;
import de.wps.bikehh.kategorien.dto.KategorieListDto;

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
		List<KategorieListDto> dtoList = kategorienAppService.getAlleKategorien();
		model.addAttribute("kategorien", dtoList);
		return "kategorien/kategorien_liste";
	}

}
