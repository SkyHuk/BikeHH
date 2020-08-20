package de.wps.bikehh.benutzerverwaltung.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ChangePasswordDto {

	@NotEmpty
	private String oldPassword;

	@NotEmpty
	@Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,64}$", message = "Das Passwort muss zwischen 8 und 64 Zeichen enthalten und darf keine Sonderzeichen enthalten.")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}