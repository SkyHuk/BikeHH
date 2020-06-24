package de.wps.bikehh.benutzerverwaltung.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import de.wps.bikehh.umfragen.material.Umfrage;

@Entity
public class Benutzer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String verschluesseltesPasswort;

	private String emailAdresse;

	private String rolle;

	private boolean istGesperrt;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "bestaetigtVonBenutzern", cascade = CascadeType.ALL)
	private List<Umfrage> bestaetigteUmfragen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerschluesseltesPasswort() {
		return verschluesseltesPasswort;
	}

	public void setVerschluesseltesPasswort(String encryptedPassword) {
		this.verschluesseltesPasswort = encryptedPassword;
	}

	public String getEmailAdresse() {
		return emailAdresse;
	}

	public void setEmailAdresse(String emailAddress) {
		this.emailAdresse = emailAddress;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String role) {
		this.rolle = role;
	}

	public boolean getIstGesperrt() {
		return istGesperrt;
	}

	public void setIstGesperrt(boolean isLocked) {
		this.istGesperrt = isLocked;
	}

	public List<Umfrage> getBestaetigteUmfragen() {
		return bestaetigteUmfragen;
	}

	public void setBestaetigteUmfragen(List<Umfrage> confirmedSurveys) {
		this.bestaetigteUmfragen = confirmedSurveys;
	}

}
