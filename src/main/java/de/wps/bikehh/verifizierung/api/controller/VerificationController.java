package de.wps.bikehh.verifizierung.api.controller;

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

import de.wps.bikehh.passwortzuruecksetzung.api.dto.RequestPasswordResetMailDto;
import de.wps.bikehh.verifizierung.service.VerificationService;

@RestController
@RequestMapping("/api/verify")
public class VerificationController {

	private VerificationService verificationService;

	@Autowired
	public VerificationController(VerificationService verifyDetailService) {
		this.verificationService = verifyDetailService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> requestVerificationMail(
			@RequestBody @Valid RequestPasswordResetMailDto requestModel) {
		try {
			verificationService.requestVerificationMail(requestModel.getEmail());
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public void verifyUser(@RequestParam String token) {
		verificationService.verifyUser(token);
	}
}
