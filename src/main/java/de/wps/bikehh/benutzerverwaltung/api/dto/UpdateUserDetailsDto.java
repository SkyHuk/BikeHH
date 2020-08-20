package de.wps.bikehh.benutzerverwaltung.api.dto;

import javax.validation.constraints.NotNull;

public class UpdateUserDetailsDto {

	@NotNull
	private String email;

	@NotNull
	private int privacySetting;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPrivacySetting() {
		return privacySetting;
	}

	public void setPrivacySetting(int privacySetting) {
		this.privacySetting = privacySetting;
	}

}
