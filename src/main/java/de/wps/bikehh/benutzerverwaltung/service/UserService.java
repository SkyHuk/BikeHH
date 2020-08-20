package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserRepository;
import de.wps.bikehh.framework.Contract;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (!userRepository.existsByEmailAddress(email)) {
			throw new UsernameNotFoundException("Unbekannter User: " + email);
		}

		User user = userRepository.findByEmailAddress(email);

		if (user.getIsLocked()) {
			throw new LockedException("Nutzer ist gesperrt");
		}
		return new BikehhUserDetails(user, email, user.getEncryptedPassword(), createAuthorities(user));
	}

	public User getUserById(long id) {
		Contract.check(userRepository.existsById(id), "userRepository.existsById(id)");

		return userRepository.findById(id).get();
	}

	public User getUserByEmail(String email) {
		Contract.notEmpty(email, "email");
		Contract.check(existsByEmail(email), "existsByEmail(email)");

		return userRepository.findByEmailAddress(email);
	}

	public boolean existsByEmail(String email) {
		Contract.notEmpty(email, "email");

		return userRepository.existsByEmailAddress(email);
	}

	public User createUser(String email, String password, String role) {
		Contract.notEmpty(email, "email");
		Contract.notEmpty(password, "password");
		Contract.notEmpty(role, "role");
		Contract.check(!existsByEmail(email), "!existsByEmail(email)");

		User user = new User(email, passwordEncoder.encode(password), role);
		return userRepository.save(user);
	}

	public void deleteUser(User user) {
		Contract.notNull(user, "user");

		userRepository.delete(user);
	}

	public User updateUser(User user, String email, int privacySetting) {
		Contract.notNull(user, "user");
		Contract.notEmpty(email, "email");
		Contract.check(!user.getIsLocked(), "!user.getIsLocked()");

		user.setEmailAddress(email);
		user.setPrivacySetting(privacySetting);
		return userRepository.save(user);
	}

	public User updatePassword(User user, String passwordOld, String passwordNew) {
		Contract.notNull(user, "user");
		Contract.notEmpty(passwordOld, "passwordOld");
		Contract.notEmpty(passwordNew, "passwordNew");
		Contract.check(passwordEncoder.matches(passwordOld, user.getEncryptedPassword()), "passwords match");

		return setPassword(user, passwordNew);
	}

	public User setPassword(User user, String password) {
		Contract.notNull(user, "user");
		Contract.notEmpty(password, "password");

		user.setEncryptedPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}

	public User verifyUser(User user) {
		Contract.notNull(user, "user");

		user.setVerified(true);
		return userRepository.save(user);
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

}
