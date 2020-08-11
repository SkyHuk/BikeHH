package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import de.wps.bikehh.umfragen.material.Umfrage;

public class ViewUmfrageDto {

	private long id;

	private String titel;
	private String kategorie;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate startDatum;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate endDatum;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate createdAt;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate updatedAt;

	private List<EditBefragungDto> befragungen;

	private String ersteller;

	private boolean istMehrfachBeantwortbar;
	private boolean istDisabled;

	public static ViewUmfrageDto from(Umfrage umfrage) {
		ViewUmfrageDto dto = new ViewUmfrageDto();
		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setKategorie(umfrage.getKategorie());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setCreatedAt(umfrage.getCreatedAt());
		dto.setUpdatedAt(umfrage.getUpdatedAt());
		dto.setBefragungen(umfrage.getBefragungen()
				.stream()
				.map(EditBefragungDto::from)
				.collect(Collectors.toList()));
		dto.setErsteller(umfrage.getErsteller().getEmailAddress());
		dto.setIstMehrfachBeantwortbar(umfrage.getIstMehrfachBeantwortbar());
		dto.setIstDisabled(umfrage.getIstDisabled());
		return null;
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

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(LocalDate startDatum) {
		this.startDatum = startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(LocalDate endDatum) {
		this.endDatum = endDatum;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<EditBefragungDto> getBefragungen() {
		return befragungen;
	}

	public void setBefragungen(List<EditBefragungDto> befragungen) {
		this.befragungen = befragungen;
	}

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	public boolean getIstMehrfachBeantwortbar() {
		return istMehrfachBeantwortbar;
	}

	public void setIstMehrfachBeantwortbar(boolean istMehrfachBeantwortbar) {
		this.istMehrfachBeantwortbar = istMehrfachBeantwortbar;
	}

	public boolean getIstDisabled() {
		return istDisabled;
	}

	public void setIstDisabled(boolean istDisabled) {
		this.istDisabled = istDisabled;
	}

}
