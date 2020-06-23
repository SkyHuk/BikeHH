package de.wps.bikehh.adminplattform.material;

import java.util.List;

public class Meldung {

	private int id;
	private double laengengrad;
	private double breitengrad;
	private int umfrageId; // leer beim postRequest, danach wird Umfrage generiert und umfrageId gesetzt
	private List<FrageAntwort> antwortenAufFragen;
	private double fahrtrichtung; // Winkel im Bogenmaß zu Norden
	private String meldungsText; // leer wenn antwort auf Meldung? mit Georg besprechen
	private Kategorie kategorie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getUmfrageId() {
		return umfrageId;
	}

	public void setUmfrageId(int umfrageId) {
		this.umfrageId = umfrageId;
	}

	public List<FrageAntwort> getAntwortenAufFragen() {
		return antwortenAufFragen;
	}

	public void setAntwortenAufFragen(List<FrageAntwort> antwortenAufFragen) {
		this.antwortenAufFragen = antwortenAufFragen;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public String getMeldungsText() {
		return meldungsText;
	}

	public void setMeldungsText(String meldungsText) {
		this.meldungsText = meldungsText;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

}
