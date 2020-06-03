package de.wps.bikehh.benutzerverwaltung.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.wps.bikehh.adfc.material.Survey;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String encryptedPassword;

	private String emailAddress;

	private String role;

	private boolean isLocked;

	@ManyToMany(mappedBy = "confirmedByUsers", cascade = CascadeType.ALL)
	private List<Survey> confirmedSurveys;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public List<Survey> getConfirmedSurveys() {
		return confirmedSurveys;
	}

	public void setConfirmedSurveys(List<Survey> confirmedSurveys) {
		this.confirmedSurveys = confirmedSurveys;
	}

}
