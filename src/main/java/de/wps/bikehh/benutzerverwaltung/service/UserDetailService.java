package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.authentifizierung.service.AuthenticationService;
import de.wps.bikehh.benutzerverwaltung.dto.request.UpdateUserDetailsDto;
import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;
import de.wps.bikehh.framework.api.exception.ApiRequestException;
import de.wps.bikehh.framework.api.exception.ErrorCode;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserAuthenticationRepository _userAuthenticationRepository;
	private VerifyDetailService _verifyDetailService;
	private PasswordDetailService _passwordDetailService;
	private AuthenticationService _authService;

	@Autowired
	public UserDetailService(UserAuthenticationRepository userAuthenticationRepository,
			VerifyDetailService verifyDetailService, PasswordDetailService passwordDetailService,
			AuthenticationService authService) {
		this._userAuthenticationRepository = userAuthenticationRepository;
		this._verifyDetailService = verifyDetailService;
		this._authService = authService;
		this._passwordDetailService = passwordDetailService;
	}

	/**
	 * gibt den User mit der entsprechenden Email zurück
	 *
	 * @param email
	 *            email des Users
	 * @return User
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		User user = _userAuthenticationRepository.findByEmailAddress(email);

		if (user.getIsLocked()) {
			throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
		}
		return new BikehhUserDetails(user, email, user.getEncryptedPassword(), createAuthorities(user));
	}

	private String[] createAuthorities(User user) {
		List<String> authorities = new ArrayList<>(3);

		switch (user.getRole()) {
		case Rollen.ROLE_ADMIN:
			authorities.add(Rollen.ROLE_ADMIN);
		case Rollen.ROLE_USER:
			authorities.add(Rollen.ROLE_USER);
			break;
		default:
			throw new RuntimeException("Unbekannte Role (" + user.getRole() + ") von User " + user.getEmailAddress());
		}

		return authorities.toArray(new String[authorities.size()]);
	}

	/**
	 * erstellt einen User
	 *
	 * @param email
	 *            email des Users
	 * @param password
	 *            passwort des Users
	 */
	public void createUser(String email, String password) throws ApiRequestException {
		createUserEntity(email, password, Rollen.ROLE_USER);
	}

	/**
	 * erstellt einen Admin
	 *
	 * @param email
	 *            email des Admins
	 * @param password
	 *            passwort des Admins
	 */
	public void createAdmin(String email, String password) {
		createUserEntity(email, password, Rollen.ROLE_ADMIN);
	}

	private void createUserEntity(String email, String password, String role) throws ApiRequestException {
		if (_userAuthenticationRepository.existsByEmailAddress(email)) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		User user = new User(email, password);
		user.setRole(role);

		user.setEncryptedPassword(passwordEncoder.encode(password));

		_userAuthenticationRepository.save(user);
		_verifyDetailService.requestVerificationMail(user.getEmailAddress());
	}

	/**
	 * aktualisiert einen User
	 *
	 * @param user
	 *            user
	 * @param userUpdate
	 *            aktualisierter User
	 */
	public void updateUser(User user, UpdateUserDetailsDto userUpdate) throws ApiRequestException {
		if (user.getIsLocked()) {
			throw new ApiRequestException(ErrorCode.unauthorized, HttpStatus.UNAUTHORIZED);
		}

		user.setEmailAddress(userUpdate.getEmail());
		user.setPrivacySetting(userUpdate.getPrivacySetting());
		_userAuthenticationRepository.save(user);
	}

	/**
	 * löscht einen User
	 *
	 * @param user
	 *            User
	 */
	public void deleteUser(User user) {
		Long userId = user.getId();

		_authService.logoutAllSession(userId);
		_verifyDetailService.deleteVerification(userId);
		_passwordDetailService.deleteResetToken(userId);

		_userAuthenticationRepository.delete(user);
	}

	/**
	 * aktualisiert Passwort eines Users
	 *
	 * @param user
	 *            User
	 * @param passwordOld
	 *            alte Passwort
	 * @param passwordNew
	 *            neue Passwort
	 */
	public void updatePassword(User user, String passwordOld, String passwordNew) throws ApiRequestException {

		if (!passwordEncoder.matches(passwordOld, user.getEncryptedPassword())) {
			throw new ApiRequestException(ErrorCode.bad_credentials, HttpStatus.BAD_REQUEST);
		}
		user.setEncryptedPassword(passwordEncoder.encode(passwordNew));
		_userAuthenticationRepository.save(user);
	}

	/**
	 * liefert alle existierende User
	 *
	 * @return eine Liste aller User
	 */
	public List<User> retrieveUsers() {
		List<User> users = new ArrayList<>();
		for (User user : _userAuthenticationRepository.findAll()) {
			if (user.getRole().equals(Rollen.ROLE_USER)) {
				user.setEncryptedPassword(null);
				users.add(user);
			}
		}

		return users;
	}

	/**
	 * gibt einen User zurück anhand seiner id
	 *
	 * @param id
	 *            id des Users
	 * @return User
	 */
	public User getUserById(Long id) {
		if (!_userAuthenticationRepository.existsById(id)) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		User user = _userAuthenticationRepository.findById(id).orElse(null);
		return user;
	}

	/**
	 * löscht einen User anhand seiner id
	 *
	 * @param id
	 *            id des Users
	 */
	public void deleteUserById(Long id) {
		if (!_userAuthenticationRepository.existsById(id)) {
			throw new ApiRequestException(ErrorCode.bad_request, HttpStatus.BAD_REQUEST);
		}

		_userAuthenticationRepository.deleteById(id);
	}

}
