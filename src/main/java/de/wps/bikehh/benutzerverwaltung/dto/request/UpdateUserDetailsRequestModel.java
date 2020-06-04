package de.wps.bikehh.benutzerverwaltung.dto.request;

public class UpdateUserDetailsRequestModel {
    private String email;
    private String password;
    private int privacySetting;

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

    public int getPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(int privacySetting) {
        this.privacySetting = privacySetting;
    }

}
