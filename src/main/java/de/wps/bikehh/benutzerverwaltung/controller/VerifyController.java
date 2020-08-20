package de.wps.bikehh.benutzerverwaltung.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.benutzerverwaltung.dto.request.RequestPasswordResetMailDto;
import de.wps.bikehh.benutzerverwaltung.service.VerifyDetailService;

@RestController
@RequestMapping("/api/verify")
public class VerifyController {

	private VerifyDetailService _verifyDetailService;

	@Autowired
	public VerifyController(VerifyDetailService verifyDetailService) {
		this._verifyDetailService = verifyDetailService;
	}

	/**
	 *
	 * schickt eine account-verifizeren Email raus
	 *
	 * @param requestModel
	 *            email
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> requestVerificationMail(@RequestBody @Valid RequestPasswordResetMailDto requestModel) {
		_verifyDetailService.requestVerificationMail(requestModel.getEmail());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 *
	 * verifiziert einen User
	 *
	 * @param token
	 *            der token, welcher den User identifiziert
	 */
	@PutMapping
	public void verifyUser(@RequestParam String token) {
		_verifyDetailService.verifyUser(token);
	}
}
