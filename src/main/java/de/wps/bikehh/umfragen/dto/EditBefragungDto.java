package de.wps.bikehh.umfragen.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class EditBefragungDto {

	private long befragungId;

	/**
	 * longitude in EPSG:3857
	 */
	private double laengengrad;

	/**
	 * latitude in EPSG:3857
	 */
	private double breitengrad;

	private double fahrtrichtung;

	@NotEmpty
	private List<FrageDto> fragen;

	public EditBefragungDto() {
		fragen = new ArrayList<>();
	}

	public long getBefragungId() {
		return befragungId;
	}

	public void setBefragungId(long befragungId) {
		this.befragungId = befragungId;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public List<FrageDto> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageDto> fragen) {
		this.fragen = fragen;
	}

}
