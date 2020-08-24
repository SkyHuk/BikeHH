package de.wps.bikehh.kategorien.api.dto;

import java.util.List;

public class KategorieRestDto {

	private long id;

	private String name;

	private List<KategorieRestDto> unterKategorien;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KategorieRestDto> getUnterKategorien() {
		return unterKategorien;
	}

	public void setUnterKategorien(List<KategorieRestDto> unterKategorien) {
		this.unterKategorien = unterKategorien;
	}
}
