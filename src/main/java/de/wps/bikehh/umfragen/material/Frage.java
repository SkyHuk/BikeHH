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
 * Entitiy Frage
 *
 * Wird in Umfrage Entity benutzt
 *
 * @author felixwolf
 *
 */

@Entity
public class Frage {

	@Id
	@GeneratedValue
	private int id;

	private String titel;

	// @ElementCollection
	// private List<Antwort> antworten = new ArrayList<Antwort>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AntwortMoeglichkeit.class)
	@JoinColumn(name = "frage", referencedColumnName = "Id", nullable = false)
	private List<AntwortMoeglichkeit> antwortMoeglichkeiten;

	/**
	 * dictionary that links a question and a answer, to be used as
	 * HashMap<Question, Answer>
	 *
	 * logic: the question this class is the instance of will only be asked when a
	 * specific QUESTION is answered with a specific ANSWER
	 */
	// private HashMap<String, String> conditions = new HashMap<String, String>();

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Bedingung.class)
	private List<Bedingung> bedingungen = new ArrayList<Bedingung>();

	private boolean erlaubeBenutzerdefinierteAntwort;

	private double breitengrad;

	private double laengengrad;

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
