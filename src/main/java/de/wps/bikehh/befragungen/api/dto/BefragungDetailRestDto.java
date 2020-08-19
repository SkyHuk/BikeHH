package de.wps.bikehh.befragungen.api.dto;

import java.util.List;

public class BefragungDetailRestDto {

	private long id;

	private List<FrageRestDto> fragen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<FrageRestDto> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageRestDto> fragen) {
		this.fragen = fragen;
	}

}
