package de.wps.bikehh.adminplattform.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Adresse {

	@Id
	@GeneratedValue
	private int id;
	private String stadt;
	private String postleitZahl;
	private String strasse;
	// not guaranteed to be present, has to be checked
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
