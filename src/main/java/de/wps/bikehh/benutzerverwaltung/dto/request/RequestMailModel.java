package de.wps.bikehh.benutzerverwaltung.dto.request;

import javax.validation.constraints.NotNull;

public class RequestMailModel {
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
