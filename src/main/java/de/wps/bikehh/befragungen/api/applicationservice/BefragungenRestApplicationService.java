package de.wps.bikehh.befragungen.api.applicationservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.beantwortung.service.BeantwortungService;
import de.wps.bikehh.befragungen.api.dto.BeantwortungDto;
import de.wps.bikehh.befragungen.api.dto.BefragungDetailRestDto;
import de.wps.bikehh.befragungen.api.dto.BefragungPositionRestDto;
import de.wps.bikehh.befragungen.api.dto.FrageRestDto;
import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.befragungen.service.BefragungenService;

@Service
public class BefragungenRestApplicationService {

	private BefragungenService befragungenService;

	private BeantwortungService beantwortungService;

	@Autowired
	public BefragungenRestApplicationService(BefragungenService service,
			BeantwortungService beantwortungService) {
		this.befragungenService = service;
		this.beantwortungService = beantwortungService;
	}

	public List<BefragungPositionRestDto> getAktuellePositionenVonBefragungen(LocalDate currentDate) {
		List<Befragung> befragungen = befragungenService.getAktuelleBefragungen(currentDate);
		List<BefragungPositionRestDto> dtoList = befragungen.stream()
				.map(this::createBefragungPositionRestDtoFromBefragung)
				.collect(Collectors.toList());
		return dtoList;
	}

	public BefragungDetailRestDto getBefragungsDetails(long befragungsId) {
		return createBefragungDetailRestDtoFromBefragung(befragungenService.getBefragung(befragungsId));
	}

	public boolean doesBefragungExist(long befragungId) {
		return befragungenService.doesBefragungExist(befragungId);
	}

	public boolean doesFrageExist(long fragenId) {
		return befragungenService.doesFrageExist(fragenId);
	}

	public void beantworteFrage(BeantwortungDto dto) {
		beantwortungService.beantworteFrage(
				befragungenService.getFrage(dto.getFragenId()),
				dto.getAntwortIndex(),
				dto.getAntwortFreitext());
	}

	private BefragungPositionRestDto createBefragungPositionRestDtoFromBefragung(Befragung befragung) {
		BefragungPositionRestDto dto = new BefragungPositionRestDto();
		dto.setId(befragung.getId());
		dto.setLaengengrad(befragung.getLaengengrad());
		dto.setBreitengrad(befragung.getBreitengrad());
		return dto;
	}

	private BefragungDetailRestDto createBefragungDetailRestDtoFromBefragung(Befragung befragung) {
		BefragungDetailRestDto dto = new BefragungDetailRestDto();
		dto.setId(befragung.getId());

		List<FrageRestDto> fragen = new ArrayList<>();
		for (Frage frage : befragung.getFragen()) {
			FrageRestDto frageDto = new FrageRestDto();
			frageDto.setId(frage.getId());
			frageDto.setText(frage.getText());
			frageDto.setHatFreitextAntwort(frage.getHatFreitextAntwort());
			frageDto.setAntworten(frage.getAntworten());
			fragen.add(frageDto);
		}
		dto.setFragen(fragen);
		return dto;
	}

}
