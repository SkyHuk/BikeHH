package de.wps.bikehh.umfragen.dto;

import de.wps.bikehh.umfragen.material.Umfrage;

public class UmfragenListeDto {

	private long id;
	private String titel;
	private String kategorie;
	private String createdAt;
	private boolean isDisabled;

	public static UmfragenListeDto from(Umfrage umfrage) {
		UmfragenListeDto dto = new UmfragenListeDto();
		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setKategorie(umfrage.getKategorie());
		dto.setCreatedAt(umfrage.getCreatedAt().toString());
		dto.setIsDisabled(umfrage.getIstDisabled());
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

}
