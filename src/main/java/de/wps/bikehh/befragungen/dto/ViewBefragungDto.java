package de.wps.bikehh.befragungen.dto;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ViewBefragungDto {

	private long id;

	private boolean istAusUmfrage;
	private boolean istAusMeldung;

	private double laengengrad;
	private double breitengrad;

	private String ersteller;

	private int anzahlFragen;
	private long anzahlAntworten;
	private int bestaetigungsSchwellenwert;

	private boolean isDisabled;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private String startDatum;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private String endDatum;

	private List<FrageBeantwortungDto> fragenMitAntworten;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isIstAusUmfrage() {
		return istAusUmfrage;
	}

	public void setIstAusUmfrage(boolean istAusUmfrage) {
		this.istAusUmfrage = istAusUmfrage;
	}

	public boolean isIstAusMeldung() {
		return istAusMeldung;
	}

	public void setIstAusMeldung(boolean istAusMeldung) {
		this.istAusMeldung = istAusMeldung;
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

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	public int getAnzahlFragen() {
		return anzahlFragen;
	}

	public void setAnzahlFragen(int anzahlFragen) {
		this.anzahlFragen = anzahlFragen;
	}

	public long getAnzahlAntworten() {
		return anzahlAntworten;
	}

	public void setAnzahlAntworten(long anzahlAntworten) {
		this.anzahlAntworten = anzahlAntworten;
	}

	public int getBestaetigungsSchwellenwert() {
		return bestaetigungsSchwellenwert;
	}

	public void setBestaetigungsSchwellenwert(int bestaetigungsSchwellenwert) {
		this.bestaetigungsSchwellenwert = bestaetigungsSchwellenwert;
	}

	public boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public List<FrageBeantwortungDto> getFragenMitAntworten() {
		return fragenMitAntworten;
	}

	public void setFragenMitAntworten(List<FrageBeantwortungDto> fragenMitAntworten) {
		this.fragenMitAntworten = fragenMitAntworten;
	}

}
