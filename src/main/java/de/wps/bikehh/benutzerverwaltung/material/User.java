package de.wps.bikehh.benutzerverwaltung.material;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String emailAddress;

    private String encryptedPassword;

    private boolean isVerified;

    private boolean isLocked;

    private int credibility;

    private int privacySetting;

    private String role;


    @Column(nullable = false)
    @CreationTimestamp
    private Date updatedAt;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;


    public User(String email, String encryptedPassword) {
        this.emailAddress = email;
        this.encryptedPassword = encryptedPassword;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean locked) {
        isLocked = locked;
    }

    public int getCredibility() {
        return credibility;
    }

    public void setCredibility(int credibility) {
        this.credibility = credibility;
    }

    public int getPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(int privacySetting) {
        this.privacySetting = privacySetting;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}


