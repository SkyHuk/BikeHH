package de.wps.bikehh.benutzerverwaltung.material;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import de.wps.bikehh.umfragen.material.Umfrage;

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

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "bestaetigtVonUsern", cascade = CascadeType.ALL)
	private List<Umfrage> bestaetigteUmfragen;

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

	public User(String email, String encryptedPassword, String role) {
		this.role = role;
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

	public List<Umfrage> getBestaetigteUmfragen() {
		return bestaetigteUmfragen;
	}

	public void setBestaetigteUmfragen(List<Umfrage> confirmedSurveys) {
		this.bestaetigteUmfragen = confirmedSurveys;
	}

}