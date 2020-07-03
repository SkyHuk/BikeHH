package de.wps.bikehh.umfragen.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Datenbank Entity
 * 
 * Eine Bedingung gehört zu einer Frage. Diese Frage soll dann nur entsprechend
 * der Bedingung gestellt werden.
 * 
 * Konkret: Stelle die Frage, zu der diese Bedingung gehört, nur, wenn andere
 * Frage mit <FRAGEID> mit der Antwort <ANTWORTID> beantwortet wurde. Diese
 * Logik muss von den mobile Anwendungen implementiert werden
 *
 */
@Entity
public class Bedingung {

	@Id
	@GeneratedValue
	private int id;

	// andere Frage
	private int frageId;

	// antwort auf diese andere Frage
	private int antwortId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFrageId() {
		return frageId;
	}

	public void setFrageId(int frageId) {
		this.frageId = frageId;
	}

	public int getAntwortId() {
		return antwortId;
	}

	public void setAntwortId(int antwortId) {
		this.antwortId = antwortId;
	}

}
