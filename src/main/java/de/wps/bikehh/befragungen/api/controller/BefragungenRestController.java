package de.wps.bikehh.befragungen.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.befragungen.api.applicationservice.BefragungenRestApplicationService;
import de.wps.bikehh.befragungen.api.dto.BeantwortungDto;
import de.wps.bikehh.befragungen.api.dto.BefragungDetailRestDto;
import de.wps.bikehh.befragungen.api.dto.BefragungPositionRestDto;
import de.wps.bikehh.framework.api.exception.ApiException;

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
	public ResponseEntity<List<BefragungPositionRestDto>> getAktuelleBefragungen() {
		List<BefragungPositionRestDto> dtoList = befragungenAppService
				.getAktuellePositionenVonBefragungen(LocalDate.now());
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@GetMapping(path = "/{befragungId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<BefragungDetailRestDto> getBefragungsDetails(@PathVariable long befragungId) {
		BefragungDetailRestDto dto = befragungenAppService.getBefragungsDetails(befragungId);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping("/{befragungId}/beantwortung")
	public ResponseEntity<Object> postAntwortAufBefragung(@PathVariable long befragungId,
			@RequestBody List<BeantwortungDto> beantwortungen) {
		if (!befragungenAppService.doesBefragungExist(befragungId)) {
			return new ResponseEntity<>(
					new ApiException(HttpStatus.BAD_REQUEST,
							"Die angegebene Befragung existiert nicht: " + befragungId),
					HttpStatus.BAD_REQUEST);
		}

		for (BeantwortungDto dto : beantwortungen) {
			if (!befragungenAppService.doesFrageExist(dto.getFragenId())) {
				return new ResponseEntity<>(
						new ApiException(HttpStatus.BAD_REQUEST,
								"Die angegebene Frage existiert nicht: " + dto.getFragenId()),
						HttpStatus.BAD_REQUEST);
			}
		}

		// Nur wenn alle Fragen existieren werden die Beantwortungen gespeichert
		beantwortungen.forEach(befragungenAppService::beantworteFrage);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
