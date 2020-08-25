package de.wps.bikehh.beantwortung.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.wps.bikehh.befragungen.material.Frage;

@Entity
public class Beantwortung {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Frage frage;

	private int antwortIndex;

	private String antwortFreitext;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Frage getFrage() {
		return frage;
	}

	public void setFrage(Frage frage) {
		this.frage = frage;
	}

	public int getAntwortIndex() {
		return antwortIndex;
	}

	public void setAntwortIndex(int antwortIndex) {
		this.antwortIndex = antwortIndex;
	}

	public String getAntwortFreitext() {
		return antwortFreitext;
	}

	public void setAntwortFreitext(String antwortFreitext) {
		this.antwortFreitext = antwortFreitext;
	}
}
