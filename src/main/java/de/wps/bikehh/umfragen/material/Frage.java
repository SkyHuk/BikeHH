package de.wps.bikehh.umfragen.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Datenbank Entitiy
 *
 * Teil einer Umfrage
 *
 */

@Entity
public class Frage {

	@Id
	@GeneratedValue
	private int id;

	// Titel beziehungsweise Fragestellung
	private String titel;

	// Liste an Antwortmoeglichkeiten
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AntwortMoeglichkeit.class)
	@JoinColumn(name = "frage", referencedColumnName = "Id", nullable = false)
	private List<AntwortMoeglichkeit> antwortMoeglichkeiten;

	// Liste an Bedingungen, die gelten müssen, damit die Frage gestellt wird
	// mehrere Umfragen sollten als logische und implementiert werden
	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Bedingung.class)
	private List<Bedingung> bedingungen = new ArrayList<Bedingung>();

	// ob diese Frage eine benutzerdefinierte-, also eine Freitextantwort erlaubt
	private boolean erlaubeBenutzerdefinierteAntwort;

	// koordinaten
	private double breitengrad;
	private double laengengrad;

	// fahrtrichtung in radiant, siehe Meldung.java für mehr informationen
	private double fahrtrichtung;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public List<Bedingung> getBedingungen() {
		return bedingungen;
	}

	public void setBedingungen(List<Bedingung> bedingungen) {
		this.bedingungen = bedingungen;
	}

	public boolean isErlaubeBenutzerdefinierteAntwort() {
		return erlaubeBenutzerdefinierteAntwort;
	}

	public void setErlaubeBenutzerdefinierteAntwort(boolean erlaubeBenutzerdefinierteAntwort) {
		this.erlaubeBenutzerdefinierteAntwort = erlaubeBenutzerdefinierteAntwort;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public void setAntwortMoeglichkeiten(List<AntwortMoeglichkeit> antwortMoeglichkeiten) {
		this.antwortMoeglichkeiten = antwortMoeglichkeiten;
	}

	public List<AntwortMoeglichkeit> getAntwortMoeglichkeiten() {
		return antwortMoeglichkeiten;
	}

}
