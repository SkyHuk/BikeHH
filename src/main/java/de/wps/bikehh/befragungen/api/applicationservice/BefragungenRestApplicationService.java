package de.wps.bikehh.befragungen.api.applicationservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.befragungen.api.dto.BefragungRestDto;
import de.wps.bikehh.befragungen.api.dto.FrageRestDto;
import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.befragungen.service.BefragungenService;

@Service
public class BefragungenRestApplicationService {

	private BefragungenService befragungenService;

	@Autowired
	public BefragungenRestApplicationService(BefragungenService service) {
		this.befragungenService = service;
	}

	public List<BefragungRestDto> getAktuelleBefragungen(LocalDate currentDate) {
		List<Befragung> befragungen = befragungenService.getAktuelleBefragungen(currentDate);
		List<BefragungRestDto> dtoList = befragungen.stream()
				.map(this::createBefragungRestDtoFromBefragung)
				.collect(Collectors.toList());
		return dtoList;
	}

	private BefragungRestDto createBefragungRestDtoFromBefragung(Befragung befragung) {
		BefragungRestDto dto = new BefragungRestDto();
		dto.setId(befragung.getId().longValue());
		dto.setLaengengrad(befragung.getLaengengrad());
		dto.setBreitengrad(befragung.getBreitengrad());

		List<FrageRestDto> fragen = new ArrayList<>();
		for (Frage frage : befragung.getFragen()) {
			FrageRestDto frageDto = new FrageRestDto();
			frageDto.setId(frage.getId().longValue());
			frageDto.setText(frage.getText());
			frageDto.setHatFreitextAntwort(frage.getHatFreitextAntwort());
			frageDto.setAntworten(frage.getAntworten());
			fragen.add(frageDto);
		}
		dto.setFragen(fragen);

		return dto;
	}
}
