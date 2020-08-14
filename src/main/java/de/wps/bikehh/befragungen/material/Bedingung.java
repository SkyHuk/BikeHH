package de.wps.bikehh.befragungen.material;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Die Bedingung, die erfüllt sein muss damit die Frage, zu der diese Bedingung
 * gehört, angezeigt wird.
 * 
 * Dazu muss die Frage mit frageId mit der Antwort antwortId beantwortet worden
 * sein.
 */
@Embeddable
public class Bedingung {

	/**
	 * Die andere Frage, die als Bedingung einbezogen wird
	 */
	@ManyToOne
	@JoinColumn(name = "target_frage", referencedColumnName = "id")
	private Frage frage;

	/**
	 * Antwort auf diese andere Frage, die als Bedingung gilt
	 */
	private int antwortIndex;

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

}
