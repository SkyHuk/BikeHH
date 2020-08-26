package de.wps.bikehh.umfragen.dto;

import java.util.List;

public class ViewUmfrageDto {

	private long id;

	private String titel;
	private String kategorie;

	private String startDatum;

	private String endDatum;

	private String createdAt;

	private String updatedAt;

	private List<EditBefragungDto> befragungen;

	private String ersteller;

	private boolean istMehrfachBeantwortbar;
	private boolean istDisabled;

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

	public String getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
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
