package de.wps.bikehh.benutzerverwaltung.dto.request;

import javax.validation.constraints.NotNull;

public class LoginDto {

	@NotNull
	private String email;

	@NotNull
	private String password;

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
