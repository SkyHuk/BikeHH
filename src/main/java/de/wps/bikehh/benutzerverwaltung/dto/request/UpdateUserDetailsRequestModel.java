package de.wps.bikehh.benutzerverwaltung.dto.request;

import javax.validation.constraints.NotNull;

public class UpdateUserDetailsRequestModel {
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
