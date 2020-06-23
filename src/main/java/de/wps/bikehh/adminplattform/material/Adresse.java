package de.wps.bikehh.adminplattform.material;

public class Adresse {

	private String stadt;
	private String postleitZahl;
	private String strasse;
	// not guaranteed to be present, has to be checked
	private String hausnummer;

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
