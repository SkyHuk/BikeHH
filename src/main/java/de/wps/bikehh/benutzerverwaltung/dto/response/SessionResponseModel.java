package de.wps.bikehh.benutzerverwaltung.dto.response;

public class SessionResponseModel {
    private String authentication;

    public SessionResponseModel(String auth){
        this.authentication = auth;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}
