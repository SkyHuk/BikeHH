package de.wps.bikehh.adminplattform.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	@ElementCollection
	private List<Antwort> antworten = new ArrayList<Antwort>();

	/**
	 * dictionary that links a question and a answer, to be used as
	 * HashMap<Question, Answer>
	 *
	 * logic: the question this class is the instance of will only be asked when a
	 * specific QUESTION is answered with a specific ANSWER
	 */
	// private HashMap<String, String> conditions = new HashMap<String, String>();

	@ElementCollection
	private List<Bedingung> bedingungen = new ArrayList<Bedingung>();

	private boolean erlaubeBenutzerdefinierteAntwort;

	private double breitengrad;

	private double laengengrad;

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

	public List<Antwort> getAntworten() {
		return antworten;
	}

	public void setAntworten(List<Antwort> antworten) {
		this.antworten = antworten;
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

	/*
	 * public HashMap<String, String> getConditions() { return conditions; }
	 * 
	 * public void setConditions(HashMap<String, String> condition) {
	 * this.conditions = condition; }
	 */

}
