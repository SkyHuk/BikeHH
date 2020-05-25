package de.wps.bikehh.benutzerverwaltung.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.BikehhUserDetails;
import de.wps.bikehh.benutzerverwaltung.material.Roles;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;

@Service
public class BikehhUserDetailsService implements UserDetailsService {

	private UserAuthenticationRepository _userAuthenticationRepository;

	@Autowired
	public BikehhUserDetailsService(UserAuthenticationRepository userAuthenticationRepository) {
		this._userAuthenticationRepository = userAuthenticationRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!_userAuthenticationRepository.existsByLogin(username)) {
			throw new UsernameNotFoundException("Unbekannter Nutzer: " + username);
		}

		User user = _userAuthenticationRepository.findByLogin(username);

		if (user.getIsLocked()) {
			throw new LockedException("Nutzer ist gesperrt");
		}
		return new BikehhUserDetails(user, username, user.getEncryptedPassword(), createAuthorities(user));
	}

	private String[] createAuthorities(User user) {
		List<String> authorities = new ArrayList<>(3);

		switch (user.getRole()) {
		case Roles.ROLE_ADMIN:
			authorities.add(Roles.ROLE_ADMIN);
		case Roles.ROLE_USER:
			authorities.add(Roles.ROLE_USER);
			break;
		default:
			throw new RuntimeException("Unbekannte Role (" + user.getRole() + ") von User " + user.getLogin());
		}

		return authorities.toArray(new String[authorities.size()]);
	}
}
