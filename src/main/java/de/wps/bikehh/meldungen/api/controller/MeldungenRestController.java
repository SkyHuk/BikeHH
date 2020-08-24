package de.wps.bikehh.meldungen.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.meldungen.api.applicationservice.MeldungenRestApplicationService;
import de.wps.bikehh.meldungen.api.dto.MeldungEinreichenRestDto;

@RestController
@RequestMapping("api/meldung")
public class MeldungenRestController {

	private MeldungenRestApplicationService meldungenAppService;

	@Autowired
	public MeldungenRestController(MeldungenRestApplicationService meldungenAppService) {
		this.meldungenAppService = meldungenAppService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void postMeldung(@ModelAttribute("user") User user,
			@Valid @RequestBody MeldungEinreichenRestDto meldung) {
		meldungenAppService.reicheMeldungEin(user, meldung);
	}
}
