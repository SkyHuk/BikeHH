package de.wps.bikehh.befragungen.applicationservice;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.beantwortung.material.Beantwortung;
import de.wps.bikehh.beantwortung.service.BeantwortungService;
import de.wps.bikehh.befragungen.dto.BefragungListDto;
import de.wps.bikehh.befragungen.dto.FrageBeantwortungDto;
import de.wps.bikehh.befragungen.dto.ViewBefragungDto;
import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.befragungen.service.BefragungenService;
import de.wps.bikehh.umfragen.dto.FrageDto;

@Service
public class BefragungenApplicationService {

	private static final DateTimeFormatter DATE_LABEL = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);

	private BefragungenService befragungenService;
	private BeantwortungService beantwortungService;

	@Autowired
	public BefragungenApplicationService(BefragungenService befragungenService,
			BeantwortungService beantwortungService) {
		this.befragungenService = befragungenService;
		this.beantwortungService = beantwortungService;
	}

	public boolean exists(long befragungId) {
		return befragungenService.doesBefragungExist(befragungId);
	}

	public ViewBefragungDto getBefragung(long befragungId) {
		ViewBefragungDto dto = new ViewBefragungDto();

		Befragung befragung = befragungenService.getBefragung(befragungId);
		dto.setId(befragungId);
		dto.setLaengengrad(befragung.getLaengengrad());
		dto.setBreitengrad(befragung.getBreitengrad());
		dto.setIsDisabled(befragung.isDisabled());
		dto.setIstAusUmfrage(befragung.istAusUmfrage());
		dto.setIstAusMeldung(befragung.istAusMeldung());
		dto.setBestaetigungsSchwellenwert(befragung.getBestaetigungsSchwellenwert());
		dto.setErsteller(befragung.getErsteller().getEmailAddress());
		dto.setStartDatum(befragung.getStartDatum().format(DATE_LABEL));
		dto.setEndDatum(befragung.getEndDatum().format(DATE_LABEL));

		int anzahlFragen = befragung.getFragen().size();
		dto.setAnzahlFragen(anzahlFragen);

		long anzahlAntworten = 0;
		for (Frage frage : befragung.getFragen()) {
			anzahlAntworten += beantwortungService.getAnzahlAntwortenAufFrage(frage);
		}
		dto.setAnzahlAntworten((anzahlAntworten > 0) ? anzahlAntworten / anzahlFragen : 0);

		List<FrageBeantwortungDto> fragenMitAntworten = new ArrayList<>();
		for (Frage frage : befragung.getFragen()) {
			FrageBeantwortungDto fdto = new FrageBeantwortungDto();

			FrageDto frageDto = new FrageDto();
			frageDto.setId(frage.getId());
			frageDto.setText(frage.getText());
			frageDto.setAntworten(frage.getAntworten());

			fdto.setFrage(frageDto);

			int[] antwortCounts = new int[frageDto.getAntworten().size()];

			List<Beantwortung> antworten = beantwortungService.getAntwortenAufFrage(frage);
			for (Beantwortung beantwortung : antworten) {
				antwortCounts[beantwortung.getAntwortIndex()]++;
			}

			List<Integer> antwortCountList = new ArrayList<>();
			for (int c : antwortCounts) {
				antwortCountList.add(c);
			}

			fdto.setAntworten(antwortCountList);
			fragenMitAntworten.add(fdto);
		}
		dto.setFragenMitAntworten(fragenMitAntworten);

		return dto;
	}

	public List<BefragungListDto> getAlleBefragungen() {
		List<BefragungListDto> dtoList = new ArrayList<>();

		List<Befragung> alleBefragungen = befragungenService.getAlleBefragungen();

		for (Befragung befragung : alleBefragungen) {
			BefragungListDto dto = new BefragungListDto();
			dto.setId(befragung.getId());
			dto.setLaengengrad(befragung.getLaengengrad());
			dto.setBreitengrad(befragung.getBreitengrad());
			dto.setIsDisabled(befragung.isDisabled());
			dto.setIstAusUmfrage(befragung.istAusUmfrage());
			dto.setIstAusMeldung(befragung.istAusMeldung());
			dto.setBestaetigungsSchwellenwert(befragung.getBestaetigungsSchwellenwert());
			dto.setErsteller(befragung.getErsteller().getEmailAddress());

			int anzahlFragen = befragung.getFragen().size();
			dto.setAnzahlFragen(anzahlFragen);

			long anzahlAntworten = 0;
			for (Frage frage : befragung.getFragen()) {
				anzahlAntworten += beantwortungService.getAnzahlAntwortenAufFrage(frage);
			}
			dto.setAnzahlAntworten((anzahlAntworten > 0) ? anzahlAntworten / anzahlFragen : 0);
			dtoList.add(dto);
		}

		return dtoList;
	}

}
