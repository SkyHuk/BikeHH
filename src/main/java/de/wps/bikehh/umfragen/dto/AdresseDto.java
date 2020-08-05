package de.wps.bikehh.umfragen.dto;

import de.wps.bikehh.umfragen.material.Adresse;

public class AdresseDto {

	private String stadt;
	private String postleitzahl;
	private String strasse;

	public static AdresseDto from(Adresse adresse) {
		AdresseDto dto = new AdresseDto();
		dto.setStadt(adresse.getStadt());
		dto.setPostleitzahl(adresse.getPostleitZahl());

		String strasse = adresse.getStrasse();
		if (adresse.hatHausnummer()) {
			strasse += " " + adresse.getHausnummer();
		}

		dto.setStrasse(strasse);
		return dto;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public void setPostleitzahl(String postleitzahl) {
		this.postleitzahl = postleitzahl;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

}
