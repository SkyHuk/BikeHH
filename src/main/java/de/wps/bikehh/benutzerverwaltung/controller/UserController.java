package de.wps.bikehh.benutzerverwaltung.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.benutzerverwaltung.dto.request.ChangePasswordDto;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsDto;
import de.wps.bikehh.benutzerverwaltung.dto.request.UserDetailsDto;
import de.wps.bikehh.benutzerverwaltung.dto.response.UserProfileDto;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.service.UserDetailService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private UserDetailService userDetailService;

	@Autowired
	public UserController(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	/**
	 *
	 * gebt den aktuellen User zurück
	 *
	 * @param auth
	 *            der aktuelle authentifizierte User
	 * @return User
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UserProfileDto getCurrentUser(@ModelAttribute("user") User user) {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setEmail(user.getEmailAddress());
		userProfileDto.setPrivacySetting(user.getPrivacySetting());
		userProfileDto.setCredibility(user.getCredibility());
		userProfileDto.setVerified(user.getIsVerified());
		return userProfileDto;
	}

	/**
	 * registriert einen neuen User
	 *
	 * @param requestUserDetails
	 *            User credentials
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void createUser(@RequestBody @Valid UserDetailsDto requestUserDetails) {
		userDetailService.createUser(requestUserDetails.getEmail(), requestUserDetails.getPassword());
	}

	/**
	 *
	 * updated einen existierenden User
	 *
	 * @param requestUserDetails
	 *            die veränderten Userdaten
	 */
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(Authentication auth,
			@RequestBody @Valid UpdateUserDetailsDto requestUserDetails) {
		User user = (User) auth.getPrincipal();

		userDetailService.updateUser(user, requestUserDetails);
	}

	/**
	 *
	 * löscht einen User Account
	 *
	 * @param auth
	 *            der aktuelle authentifizierte User
	 */
	@DeleteMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(Authentication auth) {
		User user = (User) auth.getPrincipal();

		userDetailService.deleteUser(user);
	}

	/**
	 *
	 * setzt ein neues Passwort für ein schon eingeloggter User
	 *
	 * @param auth
	 *            der aktuelle authentifizierte User
	 * @param passwordRequestModel
	 *            das neue Passwort
	 */
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public void updatePassword(Authentication auth, @Valid @RequestBody ChangePasswordDto passwordRequestModel) {
		User user = (User) auth.getPrincipal();

		String passwordOld = passwordRequestModel.getOldPassword();
		String passwordNew = passwordRequestModel.getNewPassword();

		userDetailService.updatePassword(user, passwordOld, passwordNew);
	}
}
