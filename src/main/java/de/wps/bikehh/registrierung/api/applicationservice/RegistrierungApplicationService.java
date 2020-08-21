package de.wps.bikehh.registrierung.api.applicationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.registrierung.api.dto.RegisterUserDto;
import de.wps.bikehh.verifizierung.service.VerificationService;

@Service
public class RegistrierungApplicationService {

	private UserService userService;
	private VerificationService verificationService;

	@Autowired
	public RegistrierungApplicationService(UserService userService, VerificationService verificationService) {
		this.userService = userService;
		this.verificationService = verificationService;
	}

	public void registerUser(RegisterUserDto registerUserDto) throws Exception {
		String email = registerUserDto.getEmail();

		if (userService.existsByEmail(email)) {
			throw new Exception("Ein Account mit dieser Email ist bereits registriert.");
		}

		userService.createUser(
				email,
				registerUserDto.getPassword(),
				Rollen.ROLE_USER);

		verificationService.requestVerificationMail(email);
	}
}
