package de.wps.bikehh.befragungen.applicationservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.beantwortung.service.BeantwortungService;
import de.wps.bikehh.befragungen.dto.BefragungListDto;
import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.befragungen.service.BefragungenService;

@Service
public class BefragungenApplicationService {

	private BefragungenService befragungenService;
	private BeantwortungService beantwortungService;

	@Autowired
	public BefragungenApplicationService(BefragungenService befragungenService,
			BeantwortungService beantwortungService) {
		this.befragungenService = befragungenService;
		this.beantwortungService = beantwortungService;
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
