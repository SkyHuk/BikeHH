package de.wps.bikehh.adminplattform.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bedingung {

	@Id
	@GeneratedValue
	private int id;

	private int frageId;
	private int erwarteteAntwortId;

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

	public int getErwarteteAntwortId() {
		return erwarteteAntwortId;
	}

	public void setErwarteteAntwortId(int erwarteteAntwortId) {
		this.erwarteteAntwortId = erwarteteAntwortId;
	}

}
