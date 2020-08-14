package de.wps.bikehh.befragungen.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.befragungen.api.applicationservice.BefragungenRestApplicationService;
import de.wps.bikehh.befragungen.api.dto.BefragungRestDto;

@RestController
@RequestMapping("api/befragungen")
public class BefragungenRestController {

	private BefragungenRestApplicationService befragungenAppService;

	@Autowired
	public BefragungenRestController(BefragungenRestApplicationService appService) {
		this.befragungenAppService = appService;
	}

	@GetMapping(path = "/aktuell", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<BefragungRestDto>> getAktuelleBefragungen() {
		List<BefragungRestDto> dtoList = befragungenAppService.getAktuelleBefragungen(LocalDate.now());
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

}
