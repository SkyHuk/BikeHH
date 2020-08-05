package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import de.wps.bikehh.umfragen.fachwert.Kategorie;
import de.wps.bikehh.umfragen.material.Frage;
import de.wps.bikehh.umfragen.material.Umfrage;

public class NewUmfrageDto {

	@NotEmpty(message = "Es muss ein Titel angegeben werden.")
	private String titel;

	// TODO: min und max um Gebiet einzuschränken
	private double laengengrad;
	private double breitengrad;

	private double fahrtrichtung;

	@NotNull
	private Kategorie kategorie;

	@NotNull(message = "StartDatum darf nicht leer sein.")
	private LocalDate startDatum;
	@NotNull(message = "EndDatum darf nicht leer sein.")
	private LocalDate endDatum;

	private int bestaetigungsSchwellenwert;

	// TODO: FrageDto
	private List<Frage> fragen;

	public static NewUmfrageDto from(Umfrage umfrage) {
		NewUmfrageDto dto = new NewUmfrageDto();
		dto.setTitel(umfrage.getTitel());
		dto.setLaengengrad(umfrage.getLaengengrad());
		dto.setBreitengrad(umfrage.getBreitengrad());
		dto.setFahrtrichtung(umfrage.getFahrtrichtung());
		dto.setKategorie(umfrage.getKategorie());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setBestaetigungsSchwellenwert(umfrage.getBestaetigtSchwellenwert());
		dto.setFragen(umfrage.getFragen());
		return dto;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
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

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(LocalDate startDatum) {
		this.startDatum = startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(LocalDate endDatum) {
		this.endDatum = endDatum;
	}

	public int getBestaetigungsSchwellenwert() {
		return bestaetigungsSchwellenwert;
	}

	public void setBestaetigungsSchwellenwert(int bestaetigungsSchwellenwert) {
		this.bestaetigungsSchwellenwert = bestaetigungsSchwellenwert;
	}

	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

}
