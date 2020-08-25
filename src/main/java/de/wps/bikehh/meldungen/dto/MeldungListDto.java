package de.wps.bikehh.meldungen.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class MeldungListDto {

	private long id;

	private long befragungId;

	private String ersteller;

	private double laengengrad;
	private double breitengrad;

	private String text;

	private String kategorie;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate createdAt;

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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate erstelltAm) {
		this.createdAt = erstelltAm;
	}

}
