package de.wps.bikehh.benutzerverwaltung.dto.request;

import javax.validation.constraints.Email;

public class RequestPasswordResetMailDto {

	@Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
