package de.wps.bikehh.benutzerverwaltung.dto.response;

import de.wps.bikehh.benutzerverwaltung.material.User;

public class UserDetailsResponseModel {



    private String email;
    private int privacySetting;
    private boolean isVerified;
    private int credibility;

    public UserDetailsResponseModel(User user){

        this.email = user.getEmailAddress();
        this.privacySetting = user.getPrivacySetting();
        this.isVerified = user.getIsVerified();
        this.credibility = user.getCredibility();

    }
    public UserDetailsResponseModel() {

    }

    public int getCredibility () {
        return credibility;
    }

    public int getPrivacySetting () {
        return privacySetting;
    }

    public boolean isVerified () {
        return isVerified;
    }

    public String getEmail () {
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