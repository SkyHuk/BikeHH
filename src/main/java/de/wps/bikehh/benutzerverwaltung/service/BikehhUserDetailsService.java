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
import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.material.Benutzer;
import de.wps.bikehh.benutzerverwaltung.repository.UserAuthenticationRepository;

@Service
public class BikehhUserDetailsService implements UserDetailsService {

	private UserAuthenticationRepository _userAuthenticationRepository;

	@Autowired
	public BikehhUserDetailsService(UserAuthenticationRepository userAuthenticationRepository) {
		this._userAuthenticationRepository = userAuthenticationRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (!_userAuthenticationRepository.existsByEmailAddress(email)) {
			throw new UsernameNotFoundException("Unbekannter Nutzer: " + email);
		}

		Benutzer user = _userAuthenticationRepository.findByEmailAddress(email);

		if (user.getIsLocked()) {
			throw new LockedException("Nutzer ist gesperrt");
		}
		return new BikehhUserDetails(user, email, user.getEncryptedPassword(), createAuthorities(user));
	}

	private String[] createAuthorities(Benutzer user) {
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
