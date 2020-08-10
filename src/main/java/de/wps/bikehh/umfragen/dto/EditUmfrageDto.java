package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import de.wps.bikehh.umfragen.material.Umfrage;

public class EditUmfrageDto extends NewUmfrageDto {

	private long id;

	private LocalDate createdAt;
	private LocalDate updatedAt;

	public static EditUmfrageDto from(Umfrage umfrage) {
		EditUmfrageDto dto = new EditUmfrageDto();

		// Gleiches Setup wie NewUmfrageDto
		dto.setTitel(umfrage.getTitel());
		dto.setLaengengrad(umfrage.getLaengengrad());
		dto.setBreitengrad(umfrage.getBreitengrad());
		dto.setFahrtrichtung(umfrage.getFahrtrichtung());
		dto.setKategorie(umfrage.getKategorie().getName());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setBestaetigungsSchwellenwert(umfrage.getBestaetigtSchwellenwert());
		List<FrageDto> fragen = umfrage.getFragen().stream()
				.map(FrageDto::from)
				.collect(Collectors.toList());
		dto.setFragen(fragen);

		// Neue Felder
		dto.setId(umfrage.getId());
		dto.setCreatedAt(umfrage.getCreatedAt());
		dto.setUpdatedAt(umfrage.getUpdatedAt());
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
