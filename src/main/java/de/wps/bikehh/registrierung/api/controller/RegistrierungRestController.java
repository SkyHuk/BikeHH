package de.wps.bikehh.registrierung.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.registrierung.api.applicationservice.RegistrierungApplicationService;
import de.wps.bikehh.registrierung.api.dto.RegisterUserDto;

@RestController
@RequestMapping("/api/register")
public class RegistrierungRestController {

	private RegistrierungApplicationService registerAppService;

	public RegistrierungRestController(RegistrierungApplicationService registerAppService) {
		this.registerAppService = registerAppService;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> createUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
		try {
			registerAppService.registerUser(registerUserDto);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
