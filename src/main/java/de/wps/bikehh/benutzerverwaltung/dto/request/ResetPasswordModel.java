package de.wps.bikehh.benutzerverwaltung.dto.request;

import javax.validation.constraints.NotNull;

public class ResetPasswordModel {
    @NotNull
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
