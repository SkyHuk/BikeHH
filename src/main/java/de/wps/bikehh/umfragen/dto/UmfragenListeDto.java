package de.wps.bikehh.umfragen.dto;

import de.wps.bikehh.umfragen.material.Umfrage;

public class UmfragenListeDto {

	private long id;
	private AdresseDto adresse;
	private String titel;
	private String kategorie;
	private String createdAt;
	private int anzahlBestaetigungen;
	private boolean isDisabled;

	public static UmfragenListeDto from(Umfrage umfrage) {
		UmfragenListeDto dto = new UmfragenListeDto();
		dto.setId(umfrage.getId());
		dto.setAdresse(AdresseDto.from(umfrage.getAdresse()));
		dto.setTitel(umfrage.getTitel());
		dto.setKategorie(umfrage.getKategorie().getName());
		dto.setCreatedAt(umfrage.getCreatedAt().toString());
		dto.setAnzahlBestaetigungen(umfrage.getBestaetigtVonUsern().size());
		dto.setDisabled(umfrage.getIsDisabled());
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AdresseDto getAdresse() {
		return adresse;
	}

	public void setAdresse(AdresseDto adresse) {
		this.adresse = adresse;
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

	public int getAnzahlBestaetigungen() {
		return anzahlBestaetigungen;
	}

	public void setAnzahlBestaetigungen(int anzahlBestaetigungen) {
		this.anzahlBestaetigungen = anzahlBestaetigungen;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

}
