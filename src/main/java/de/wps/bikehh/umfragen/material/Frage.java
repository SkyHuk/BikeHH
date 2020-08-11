package de.wps.bikehh.umfragen.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Eine Frage als Teil einer Befragung.
 */
@Entity
public class Frage {

	@Id
	private long id;

	@ManyToOne
	private Befragung befragung;

	/**
	 * Die Fragestellung selbst.
	 */
	private String text;

	@ElementCollection
	private List<String> antworten;

	/**
	 * Liste an Bedingungen, die gelten müssen, damit die Frage gestellt wird.
	 */
	@ElementCollection
	private List<Bedingung> bedingungen = new ArrayList<Bedingung>();

	/**
	 * Ob diese Frage eine Freitextantwort erlaubt.
	 */
	private boolean hatFreitextAntwort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Befragung getBefragung() {
		return befragung;
	}

	public void setBefragung(Befragung befragung) {
		this.befragung = befragung;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getAntworten() {
		return antworten;
	}

	public void setAntworten(List<String> antworten) {
		this.antworten = antworten;
	}

	public List<Bedingung> getBedingungen() {
		return bedingungen;
	}

	public void setBedingungen(List<Bedingung> bedingungen) {
		this.bedingungen = bedingungen;
	}

	public boolean getHatFreitextAntwort() {
		return hatFreitextAntwort;
	}

	public void setHatFreitextAntwort(boolean hatFreitextAntwort) {
		this.hatFreitextAntwort = hatFreitextAntwort;
	}

}
