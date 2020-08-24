package de.wps.bikehh.kategorien.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.kategorien.api.applicationservice.KategorienRestApplicationService;
import de.wps.bikehh.kategorien.api.dto.KategorieRestDto;

@RestController
@RequestMapping("api/kategorien")
public class KategorienRestController {

	private KategorienRestApplicationService kategorienAppService;

	public KategorienRestController(KategorienRestApplicationService kategorienAppService) {
		this.kategorienAppService = kategorienAppService;
	}

	@GetMapping
	@ResponseBody
	public List<KategorieRestDto> getAlleKategorien() {
		return kategorienAppService.getAlleKategorien();
	}

}
