package de.wps.bikehh.umfragen.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class FrageDto {

	private long id;

	@NotNull
	private String text;
	private List<String> antworten;
	private boolean hatFreitextAntwort;

	// TODO: Bedingungen

	public FrageDto() {
		antworten = new ArrayList<>();
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

	public boolean getHatFreitextAntwort() {
		return hatFreitextAntwort;
	}

	public void setHatFreitextAntwort(boolean hatFreitextAntwort) {
		this.hatFreitextAntwort = hatFreitextAntwort;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
