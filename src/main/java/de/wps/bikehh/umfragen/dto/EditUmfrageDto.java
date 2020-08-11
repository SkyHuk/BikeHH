package de.wps.bikehh.umfragen.dto;

import java.util.stream.Collectors;

import de.wps.bikehh.umfragen.material.Umfrage;

public class EditUmfrageDto extends NewUmfrageDto {

	private Long id;

	public static EditUmfrageDto from(Umfrage umfrage) {
		EditUmfrageDto dto = new EditUmfrageDto();
		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setKategorie(umfrage.getKategorie());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setBefragungen(umfrage.getBefragungen()
				.stream()
				.map(EditBefragungDto::from)
				.collect(Collectors.toList()));
		dto.setIstMehrfachBeantwortbar(umfrage.getIstMehrfachBeantwortbar());
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
