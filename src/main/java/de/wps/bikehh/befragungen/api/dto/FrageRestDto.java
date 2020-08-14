package de.wps.bikehh.befragungen.api.dto;

import java.util.List;

public class FrageRestDto {

	private long id;
	private String text;
	private List<String> antworten;
	private boolean hatFreitextAntwort;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isHatFreitextAntwort() {
		return hatFreitextAntwort;
	}

	public void setHatFreitextAntwort(boolean hatFreitextAntwort) {
		this.hatFreitextAntwort = hatFreitextAntwort;
	}

}
