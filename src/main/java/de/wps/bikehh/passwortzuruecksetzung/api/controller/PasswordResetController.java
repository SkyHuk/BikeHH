package de.wps.bikehh.passwortzuruecksetzung.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.passwortzuruecksetzung.api.dto.RequestPasswordResetMailDto;
import de.wps.bikehh.passwortzuruecksetzung.api.dto.ResetPasswordDto;
import de.wps.bikehh.passwortzuruecksetzung.service.PasswordResetService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

	private PasswordResetService passwordDetailService;

	@Autowired
	public PasswordResetController(PasswordResetService passwordDetailService) {
		this.passwordDetailService = passwordDetailService;
	}

	@PostMapping(value = "/forgot", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void requestPasswordResetMail(@RequestBody @Valid RequestPasswordResetMailDto requestModel) {
		String email = requestModel.getEmail();

		passwordDetailService.requestResetMail(email);
	}

	@PostMapping(value = "/reset", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void resetPassword(@RequestBody @Valid ResetPasswordDto requestModel, @RequestParam String token) {
		passwordDetailService.resetPassword(requestModel.getNewPassword(), token);
	}

}
