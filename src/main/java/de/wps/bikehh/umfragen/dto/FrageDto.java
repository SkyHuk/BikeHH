package de.wps.bikehh.umfragen.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import de.wps.bikehh.befragungen.material.Frage;

public class FrageDto {

	@NotNull
	private String text;
	private List<String> antworten;
	private boolean hatFreitextAntwort;

	// TODO: Bedingungen

	public FrageDto() {
		antworten = new ArrayList<>();
		antworten.add("Ja");
		antworten.add("Nein");
	}

	public static FrageDto from(Frage frage) {
		FrageDto dto = new FrageDto();
		dto.setText(frage.getText());
		dto.setHatFreitextAntwort(frage.getHatFreitextAntwort());
		dto.setAntworten(frage.getAntworten());
		return dto;
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

}
