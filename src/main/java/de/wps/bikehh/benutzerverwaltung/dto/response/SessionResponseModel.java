package de.wps.bikehh.benutzerverwaltung.dto.response;

public class SessionResponseModel {
    private String accessToken;

    public SessionResponseModel(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
