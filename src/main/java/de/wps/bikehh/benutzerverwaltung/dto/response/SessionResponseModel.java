package de.wps.bikehh.benutzerverwaltung.dto.response;

public class SessionResponseModel {
    private String Authorization;

    public SessionResponseModel(String access) {
        this.Authorization = access;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }
}
