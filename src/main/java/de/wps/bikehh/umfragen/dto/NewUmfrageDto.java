package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class NewUmfrageDto {

	@NotEmpty(message = "Es muss ein Titel angegeben werden.")
	private String titel;

	@NotEmpty(message = "Es muss eine Kategorie angegeben werden.")
	private String kategorie;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate startDatum;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate endDatum;

	@NotEmpty
	private List<EditBefragungDto> befragungen;

	private boolean istMehrfachBeantwortbar;

	public NewUmfrageDto() {
		befragungen = new ArrayList<>();
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

	public List<EditBefragungDto> getBefragungen() {
		return befragungen;
	}

	public void setBefragungen(List<EditBefragungDto> befragungen) {
		this.befragungen = befragungen;
	}

	public boolean getIstMehrfachBeantwortbar() {
		return istMehrfachBeantwortbar;
	}

	public void setIstMehrfachBeantwortbar(boolean istMehrfachBeantwortbar) {
		this.istMehrfachBeantwortbar = istMehrfachBeantwortbar;
	}

}
