package de.wps.bikehh.meldungen.api.dto;

import javax.validation.constraints.NotEmpty;

public class MeldungEinreichenRestDto {

	private double laengengrad;

	private double breitengrad;

	private String text;

	@NotEmpty
	private String kategorie;

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

}
