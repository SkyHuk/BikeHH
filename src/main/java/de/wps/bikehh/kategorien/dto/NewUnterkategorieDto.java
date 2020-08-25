package de.wps.bikehh.kategorien.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class NewUnterkategorieDto {

	private long oberKategorieId;

	private String oberKategorieName;

	@NotEmpty(message = "Der Name darf nicht leer sein.")
	@Length(min = 3, max = 255, message = "Der Name muss zwischen 3 und 255 Zeichen haben.")
	private String name;

	public long getOberKategorieId() {
		return oberKategorieId;
	}

	public void setOberKategorieId(long oberKategorieId) {
		this.oberKategorieId = oberKategorieId;
	}

	public String getOberKategorieName() {
		return oberKategorieName;
	}

	public void setOberKategorieName(String oberKategorieName) {
		this.oberKategorieName = oberKategorieName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
