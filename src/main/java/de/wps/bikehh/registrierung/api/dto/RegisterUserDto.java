package de.wps.bikehh.registrierung.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class RegisterUserDto {

	@NotEmpty
	@Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
	private String email;

	@NotEmpty(message = "Das Passwort darf nicht leer sein.")
	@Length(min = 8, max = 64, message = "Das Passwort muss zwischen 8 und 64 Zeichen enthalten.")
	@Pattern(regexp = ".*\\d.*", message = "Das Passwort muss mindestens eine Zahl enthalten.")
	@Pattern(regexp = ".*[a-z].*", message = "Das Passwort muss mindestens einen Kleinbuchstaben enthalten.")
	@Pattern(regexp = ".*[A-Z].*", message = "Das Passwort muss mindestens einen Großbuchstaben enthalten.")
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
