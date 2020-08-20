package de.wps.bikehh.benutzerverwaltung.dto.response;

public class UserProfileDto {

	private String email;
	private int privacySetting;
	private boolean isVerified;
	private int credibility;

	public int getCredibility() {
		return credibility;
	}

	public int getPrivacySetting() {
		return privacySetting;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPrivacySetting(int privacySetting) {
		this.privacySetting = privacySetting;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public void setCredibility(int credibility) {
		this.credibility = credibility;
	}

}