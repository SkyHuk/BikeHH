package de.wps.bikehh.adminplattform.material;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Meldung {

	@Id
	@GeneratedValue
	private int id;

	private double laengengrad;
	private double breitengrad;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Umfrage.class)
	private Umfrage umfrage; // leer beim postRequest, danach wird Umfrage generiert und umfrageId gesetzt
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Antwort.class)
	private List<Antwort> antwortenAufFragen;

	private double fahrtrichtung; // Winkel im Bogenmaß zu Norden
	private String text; // leer wenn antwort auf Meldung? mit Georg besprechen

	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	// private Kategorie kategorie;

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
