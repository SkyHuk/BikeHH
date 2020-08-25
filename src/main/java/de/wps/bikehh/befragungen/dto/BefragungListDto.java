package de.wps.bikehh.befragungen.dto;

public class BefragungListDto {

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getIstAusUmfrage() {
		return istAusUmfrage;
	}

	public void setIstAusUmfrage(boolean ausUmfrage) {
		this.istAusUmfrage = ausUmfrage;
	}

	public boolean getIstAusMeldung() {
		return istAusMeldung;
	}

	public void setIstAusMeldung(boolean ausMeldung) {
		this.istAusMeldung = ausMeldung;
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
}
