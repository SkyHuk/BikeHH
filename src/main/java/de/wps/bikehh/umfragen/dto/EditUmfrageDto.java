package de.wps.bikehh.umfragen.dto;

import java.time.LocalDate;

import de.wps.bikehh.umfragen.material.Umfrage;

public class EditUmfrageDto extends NewUmfrageDto {

	private long id;

	private LocalDate createdAt;
	private LocalDate updatedAt;

	public static EditUmfrageDto from(Umfrage umfrage) {
		EditUmfrageDto dto = (EditUmfrageDto) NewUmfrageDto.from(umfrage);
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
