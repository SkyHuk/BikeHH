package de.wps.bikehh.umfragen.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Datenbank Entity
 * 
 * Teil einer Umfrage
 *
 */
@Entity
public class Adresse {

	@Id
	@GeneratedValue
	private int id;
	private String stadt;
	private String postleitZahl;
	private String strasse;
	// hausnummer sind nicht immer gesetzt, weil die openstreetmap API nicht zu
	// jeden Koordinaten eine Hausnummer findet, sollte auf null gecheckt werden
	private String hausnummer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getPostleitZahl() {
		return postleitZahl;
	}

	public void setPostleitZahl(String postleitZahl) {
		this.postleitZahl = postleitZahl;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String straße) {
		this.strasse = straße;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public boolean hatHausnummer(Adresse addresse) {
		return addresse.getHausnummer() != null;
	}

}
