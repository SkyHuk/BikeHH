package de.wps.bikehh.passwortzuruecksetzung.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ResetPasswordDto {

	@NotEmpty
	@Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,64}$", message = "Das Passwort muss zwischen 8 und 64 Zeichen enthalten und darf keine Sonderzeichen enthalten.")
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
