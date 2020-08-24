package de.wps.bikehh.kategorien.dto;

import java.util.List;

public class KategorieListDto {

	private long id;

	private String name;

	private List<KategorieListDto> unterKategorien;

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

	public List<KategorieListDto> getUnterKategorien() {
		return unterKategorien;
	}

	public void setUnterKategorien(List<KategorieListDto> unterKategorien) {
		this.unterKategorien = unterKategorien;
	}

}
