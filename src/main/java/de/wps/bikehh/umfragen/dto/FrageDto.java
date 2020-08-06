package de.wps.bikehh.umfragen.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import de.wps.bikehh.umfragen.material.Frage;

public class FrageDto {

	@NotNull
	private String titel;
	private List<String> antworten;

	public FrageDto() {
		antworten = new ArrayList<>();
	}

	public static FrageDto from(Frage frage) {
		FrageDto dto = new FrageDto();
		dto.setTitel(frage.getTitel());
		List<String> antworten = frage.getAntwortMoeglichkeiten().stream()
				.map(antwort -> antwort.getText())
				.collect(Collectors.toList());
		dto.setAntworten(antworten);
		return dto;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public List<String> getAntworten() {
		return antworten;
	}

	public void setAntworten(List<String> antworten) {
		this.antworten = antworten;
	}

}
