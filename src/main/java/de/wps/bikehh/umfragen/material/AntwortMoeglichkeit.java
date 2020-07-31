package de.wps.bikehh.umfragen.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Eine Antwortmöglichkeit ist eine eine mögliche Antwort auf eine Frage einer
 * Umfrage, die durch den Admin beim Erstellen einer Umfrage angegeben wird
 */
@Entity
public class AntwortMoeglichkeit {

	@Id
	@GeneratedValue
	private int id;

	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
