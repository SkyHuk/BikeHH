package de.wps.bikehh.umfragen.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import de.wps.bikehh.umfragen.material.Befragung;

public class EditBefragungDto {

	private Long befragungId;

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

	public static EditBefragungDto from(Befragung befragung) {
		EditBefragungDto dto = new EditBefragungDto();
		dto.setBefragungId(befragung.getId());
		dto.setLaengengrad(befragung.getLaengengrad());
		dto.setBreitengrad(befragung.getBreitengrad());
		dto.setFahrtrichtung(befragung.getFahrtrichtung());
		dto.setFragen(befragung.getFragen()
				.stream()
				.map(FrageDto::from)
				.collect(Collectors.toList()));
		return dto;
	}

	public Long getBefragungId() {
		return befragungId;
	}

	public void setBefragungId(Long befragungId) {
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
