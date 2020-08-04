package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import de.wps.bikehh.umfragen.fachwert.Kategorie;
import de.wps.bikehh.umfragen.material.Adresse;
import de.wps.bikehh.umfragen.material.Frage;
import de.wps.bikehh.umfragen.material.Umfrage;

public class UmfrageDto {

	private long id;

	@NotEmpty(message = "Es muss ein Titel angegeben werden.")
	private String titel;

	private double laengengrad;
	private double breitengrad;

	@NotNull
	private Kategorie kategorie;

	@NotNull(message = "StartDatum darf nicht leer sein.")
	private LocalDate startDatum;
	@NotNull(message = "EndDatum darf nicht leer sein.")
	private LocalDate endDatum;

	private LocalDate createdAt;
	private LocalDate updatedAt;

	private boolean verified;

	private double fahrtrichtung;
	private List<String> bestaetigtVonUsernames;
	private int bestaetigtSchwellenwert;
	private List<Frage> fragen;
	private String erstellerName;
	private Adresse adresse;
	private boolean isAutomatischErstellt;
	private boolean isDisabled;

	public static UmfrageDto from(Umfrage umfrage) {
		UmfrageDto dto = new UmfrageDto();
		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setLaengengrad(umfrage.getLaengengrad());
		dto.setBreitengrad(umfrage.getBreitengrad());
		dto.setKategorie(umfrage.getKategorie());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setCreatedAt(umfrage.getCreatedAt());
		dto.setUpdatedAt(umfrage.getBearbeitetAmDatum());
		dto.setVerified(umfrage.getIstBestaetigt());
		dto.setFahrtrichtung(umfrage.getFahrtrichtung());
		dto.setBestaetigtVonUsernames(umfrage.getBestaetigtVonUsern().stream().map(user -> user.getEmailAddress())
				.collect(Collectors.toList()));
		dto.setBestaetigtSchwellenwert(umfrage.getBestaetigtSchwellenwert());
		dto.setFragen(umfrage.getFragen());
		dto.setErstellerName(umfrage.getErsteller().getEmailAddress());
		dto.setAdresse(umfrage.getAdresse());
		dto.setAutomatischErstellt(!umfrage.getManuellErstellt());
		dto.setDisabled(umfrage.getIsDisabled());
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public List<String> getBestaetigtVonUsernames() {
		return bestaetigtVonUsernames;
	}

	public void setBestaetigtVonUsernames(List<String> bestaetigtVonUsernames) {
		this.bestaetigtVonUsernames = bestaetigtVonUsernames;
	}

	public int getBestaetigtSchwellenwert() {
		return bestaetigtSchwellenwert;
	}

	public void setBestaetigtSchwellenwert(int bestaetigtSchwellenwert) {
		this.bestaetigtSchwellenwert = bestaetigtSchwellenwert;
	}

	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

	public String getErstellerName() {
		return erstellerName;
	}

	public void setErstellerName(String erstellerName) {
		this.erstellerName = erstellerName;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public boolean isAutomatischErstellt() {
		return isAutomatischErstellt;
	}

	public void setAutomatischErstellt(boolean isAutomatischErstellt) {
		this.isAutomatischErstellt = isAutomatischErstellt;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

}
