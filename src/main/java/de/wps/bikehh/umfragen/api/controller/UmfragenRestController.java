package de.wps.bikehh.umfragen.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.umfragen.api.applicationservice.UmfragenRestApplicationService;
import de.wps.bikehh.umfragen.api.dto.UmfrageAntwortRestDto;

@RestController
@RequestMapping("api/umfragen")
public class UmfragenRestController {

	private UmfragenRestApplicationService umfragenAppService;

	@Autowired
	public UmfragenRestController(UmfragenRestApplicationService umfragenAppService) {
		this.umfragenAppService = umfragenAppService;
	}

	@PostMapping(value = "/antwort", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<String> postUmfrageAntwort(@RequestBody UmfrageAntwortRestDto umfrageAntwort) {
		if (!umfragenAppService.hasUmfrage(umfrageAntwort.getUmfrageId())) {
			return new ResponseEntity<>("Eine Umfrage mit gegebener ID existiert nicht!",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		umfragenAppService.beantworteUmfrage(umfrageAntwort);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
