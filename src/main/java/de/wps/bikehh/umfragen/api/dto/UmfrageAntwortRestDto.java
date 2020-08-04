package de.wps.bikehh.umfragen.api.dto;

import java.util.Map;

public class UmfrageAntwortRestDto {

	private long umfrageId;

	private long userId;

	/**
	 * Key ist der index der Frage, Value ist die gewählte Antwort oder die
	 * Freitextantwort als String.
	 */
	private Map<Integer, ?> antworten;

	public long getUmfrageId() {
		return umfrageId;
	}

	public void setUmfrageId(long umfrageId) {
		this.umfrageId = umfrageId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Map<Integer, ?> getAntworten() {
		return antworten;
	}

	public void setAntworten(Map<Integer, ?> antworten) {
		this.antworten = antworten;
	}

}
