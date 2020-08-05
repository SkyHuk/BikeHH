package de.wps.bikehh.karte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;
import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;

@Controller
@RequestMapping("map")
public class KartenController {

	private UmfragenApplicationService umfragenAppService;

	@Autowired
	public KartenController(UmfragenApplicationService umfragenAppService) {
		this.umfragenAppService = umfragenAppService;
	}

	@GetMapping
	public String getMap(Model model) {
		List<ViewUmfrageDto> umfragen = umfragenAppService.getAlleUmfragen();
		model.addAttribute("umfragen", umfragen);
		return "map/map";
	}
}
