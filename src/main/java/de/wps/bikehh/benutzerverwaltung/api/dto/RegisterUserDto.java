package de.wps.bikehh.benutzerverwaltung.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class RegisterUserDto {

	@Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
	private String email;

	@NotEmpty
	@Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,64}$", message = "Das Passwort muss zwischen 8 und 64 Zeichen enthalten und darf keine Sonderzeichen enthalten.")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
