package de.wps.bikehh.befragungen.api.dto;

import java.util.List;

public class BefragungRestDto {

	private long id;

	/**
	 * longitude in EPSG:3857
	 */
	private double laengengrad;

	/**
	 * latitude in EPSG:3857
	 */
	private double breitengrad;

	// TODO: Fahrtrichtung
	// private double fahrtrichtung;

	private List<FrageRestDto> fragen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<FrageRestDto> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageRestDto> fragen) {
		this.fragen = fragen;
	}

}
