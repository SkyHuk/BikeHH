package de.wps.bikehh.adminplattform.material;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Antwort {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Meldung.class)
	private Meldung meldung;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Frage.class)
	private Frage frage;

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
