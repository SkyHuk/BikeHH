package de.wps.bikehh.meldungen.dto;

public class MeldungListDto {

	private long id;

	private long befragungId;

	private String ersteller;

	private double laengengrad;
	private double breitengrad;

	private String text;

	private String kategorie;

	private String createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBefragungId() {
		return befragungId;
	}

	public void setBefragungId(long befragungId) {
		this.befragungId = befragungId;
	}

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String erstelltAm) {
		this.createdAt = erstelltAm;
	}

}
