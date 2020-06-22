package de.wps.bikehh.adminplattform.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bedingung {

	@Id
	@GeneratedValue
	private int id;

	private String frage;
	private String erwarteteAntwort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrage() {
		return frage;
	}

	public void setFrage(String frage) {
		this.frage = frage;
	}

	public String getErwarteteAntwort() {
		return erwarteteAntwort;
	}

	public void setErwarteteAntwort(String erwarteteAntwort) {
		this.erwarteteAntwort = erwarteteAntwort;
	}

}
