package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import de.wps.bikehh.umfragen.material.Umfrage;

public class NewUmfrageDto {

	@NotEmpty(message = "Es muss ein Titel angegeben werden.")
	private String titel;

	// TODO: min und max um Gebiet einzuschränken
	private double laengengrad;
	private double breitengrad;

	private double fahrtrichtung;

	// TODO: Kategorie machen
	private String kategorie;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate startDatum;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate endDatum;

	private int bestaetigungsSchwellenwert;

	private List<FrageDto> fragen;

	public NewUmfrageDto() {
		fragen = new ArrayList<>();
		fragen.add(new FrageDto());
	}

	public static NewUmfrageDto from(Umfrage umfrage) {
		NewUmfrageDto dto = new NewUmfrageDto();
		dto.setTitel(umfrage.getTitel());
		dto.setLaengengrad(umfrage.getLaengengrad());
		dto.setBreitengrad(umfrage.getBreitengrad());
		dto.setFahrtrichtung(umfrage.getFahrtrichtung());
		dto.setKategorie(umfrage.getKategorie().getName());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setBestaetigungsSchwellenwert(umfrage.getBestaetigtSchwellenwert());
		List<FrageDto> fragen = umfrage.getFragen().stream()
				.map(FrageDto::from)
				.collect(Collectors.toList());
		dto.setFragen(fragen);
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

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
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

	public List<FrageDto> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageDto> fragen) {
		this.fragen = fragen;
	}

}
