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
 * Datenbank Entity dieses Model gibt die Struktur für die mobile apps vor, nach
 * der die JSONs aufgebaut werden müssen
 * 
 * TODO: Überprüfen, ob vollständig. Alle Funktionalitäten abgedeckt?
 *
 */
@Entity
public class Meldung {

	@Id
	@GeneratedValue
	private int id;

	// koordinaten der Meldung
	private double laengengrad;
	private double breitengrad;

	// die Umfrage, auf die die Meldung antwortet
	// muss nicht gesetzt sein, wenn diese Meldung nicht auf eine Umfrage antwortet
	// sondern neu von einem Radfahrer erzeugt wurde
	// leer beim postRequest, danach wird Umfrage generiert und gesetzt
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Umfrage.class)
	private Umfrage umfrage;

	// Liste an Antworten. Leer bei Neuerstellung
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Antwort.class)
	private List<Antwort> antwortenAufFragen;

	// Winkel im Bogenmaß nach Osten (Wertebereich 0 - 2pi, nach Osten weil das
	// mathematischer Standard ist)
	private double fahrtrichtung;

	// nur gefüllt wenn Meldung neu generiert
	private String text;

	// TODO: Kategorie ordentlich einbauen (Liste an vorgegebenen Kategorien)
	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	// private Kategorie kategorie;

	// TODO: Meldung soll Foto beinhalten können

	protected Meldung() {
		// required by JPA
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

	/*
	 * public Kategorie getKategorie() { return kategorie; }
	 * 
	 * public void setKategorie(Kategorie kategorie) { this.kategorie = kategorie; }
	 */

}
