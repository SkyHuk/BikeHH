package de.wps.bikehh.meldungen.material;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.wps.bikehh.umfragen.material.Umfrage;

/**
 * Dieses Model gibt die Struktur für die mobile apps vor, nach welcher die DTOs
 * aufgebaut werden müssen
 * 
 * TODO: Auf Vollständigkeit prüfen. Alle Funktionalitäten abgedeckt?
 */
@Entity
public class Meldung {

	@Id
	@GeneratedValue
	private int id;

	private double laengengrad;
	private double breitengrad;

	/**
	 * Die Umfrage, auf die die Meldung antwortet muss nicht gesetzt sein wenn
	 * diese Meldung nicht auf eine Umfrage antwortet sondern neu von einem
	 * Radfahrer erzeugt wurde. Dann ist sie leer beim postRequest und wird
	 * danach generiert und gesetzt
	 */
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Umfrage.class)
	private Umfrage umfrage;

	/**
	 * Liste an Antworten. Leer bei Neuerstellung
	 */
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Antwort.class)
	private List<Antwort> antwortenAufFragen;

	/**
	 * Winkel im Bogenmaß nach Osten (Wertebereich 0 - 2pi, nach Osten weil das
	 * mathematischer Standard ist) eine fahrtrichtung von 0.0 ist ungültig und
	 * steht symbolisierend für "keine Fahrtrichtung gesetzt"
	 */
	private double fahrtrichtung;

	/**
	 * Nur gefüllt wenn Meldung neu generiert wird
	 */
	private String text;

	// TODO: Kategorie ordentlich einbauen (Liste an vorgegebenen Kategorien)
	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	// private Kategorie kategorie;

	// TODO: Meldung soll Foto beinhalten können

	protected Meldung() {
	}

	public Meldung(int id, double laengengrad, double breitengrad, Umfrage umfrage, List<Antwort> antwortenAufFragen,
			double fahrtrichtung, String text) {
		this.id = id;
		this.laengengrad = laengengrad;
		this.breitengrad = breitengrad;
		this.umfrage = umfrage;
		this.antwortenAufFragen = antwortenAufFragen;
		this.fahrtrichtung = fahrtrichtung;
		this.text = text;
	}

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

	public Umfrage getUmfrage() {
		return umfrage;
	}

	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
	}

	public List<Antwort> getAntwortenAufFragen() {
		return antwortenAufFragen;
	}

	public void setAntwortenAufFragen(List<Antwort> antwortenAufFragen) {
		this.antwortenAufFragen = antwortenAufFragen;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
