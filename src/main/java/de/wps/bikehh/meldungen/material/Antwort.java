package de.wps.bikehh.meldungen.material;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.wps.bikehh.umfragen.material.Frage;

/**
 * 
 * Datenbank Entity Diese Klasse ist eine Antwort auf eine Umfrage, die als
 * automatisch aus einer Meldung generiert wurde
 * 
 * allgemein ist der ganze Meldungs-Bereich der Plattform nur skizzenhaft
 * implementiert
 * 
 *
 */
@Entity
public class Antwort {

	@Id
	@GeneratedValue
	private int id;

	// die Meldung, in der sich diese Antwort befindet
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Meldung.class)
	private Meldung meldung;

	// die Frage, auf die diese Antwort antwortet (Frage siehe umfrage.material)
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Frage.class)
	private Frage frage;

	// die tatsächliche Antwort
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Meldung getMeldung() {
		return meldung;
	}

	public void setMeldung(Meldung meldung) {
		this.meldung = meldung;
	}

	public Frage getFrage() {
		return frage;
	}

	public void setFrage(Frage frage) {
		this.frage = frage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
