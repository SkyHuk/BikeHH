package de.wps.bikehh.befragungen.dto;

import java.util.List;

import de.wps.bikehh.umfragen.dto.FrageDto;

public class FrageBeantwortungDto {

	private FrageDto frage;

	private List<Integer> antworten;

	public FrageDto getFrage() {
		return frage;
	}

	public void setFrage(FrageDto frage) {
		this.frage = frage;
	}

	public List<Integer> getAntworten() {
		return antworten;
	}

	public void setAntworten(List<Integer> antworten) {
		this.antworten = antworten;
	}

}
