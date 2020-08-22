package de.wps.bikehh.authentifizierung.material;

import java.time.LocalDate;

import de.wps.bikehh.benutzerverwaltung.material.User;

public class Session {

	private String token;

	private User user;

	private LocalDate createdAt;

	public Session(String token, User user) {
		this.token = token;
		this.user = user;
		this.createdAt = LocalDate.now();
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}
}
