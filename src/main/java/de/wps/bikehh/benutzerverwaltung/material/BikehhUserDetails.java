package de.wps.bikehh.benutzerverwaltung.material;

import org.springframework.security.core.authority.AuthorityUtils;

public class BikehhUserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -6532889422081326223L;

	private User user;

	public BikehhUserDetails(User user, String username, String password, String[] authorities) {
		super(username, password, AuthorityUtils.createAuthorityList(authorities));
		this.user = user;
	}

	public User getBikehhUser() {
		return user;
	}

}
