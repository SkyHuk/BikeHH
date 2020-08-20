package de.wps.bikehh.benutzerverwaltung.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.benutzerverwaltung.dto.request.LoginDto;
import de.wps.bikehh.benutzerverwaltung.dto.response.AuthTokenDto;
import de.wps.bikehh.benutzerverwaltung.material.Session;
import de.wps.bikehh.benutzerverwaltung.security.OAuthToken;
import de.wps.bikehh.benutzerverwaltung.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService _authService;

	@Autowired
	public AuthController(AuthService authService) {
		this._authService = authService;
	}

	/**
	 * Versucht einen User mit gegebener Email-Adresse zu authorisieren.
	 *
	 * @param loginrequest
	 *            user credentials
	 * @return Eine mit gegebenen Token gültige Session
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public AuthTokenDto login(@Valid @RequestBody LoginDto loginrequest) {
		String email = loginrequest.getEmail();
		String password = loginrequest.getPassword();

		Session session = _authService.loginUser(email, password);
		AuthTokenDto responseDto = new AuthTokenDto();
		responseDto.setToken(session.getToken());
		return responseDto;
	}

	/**
	 * loggt einen existierenden User aus
	 *
	 * @param auth
	 *            der aktuelle authentifizierte User
	 */
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(Authentication auth) {
		Session session = ((OAuthToken) auth).getSession();

		_authService.logoutUser(session);
	}
}
