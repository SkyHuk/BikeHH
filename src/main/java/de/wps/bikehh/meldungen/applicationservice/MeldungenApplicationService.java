package de.wps.bikehh.meldungen.applicationservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import de.wps.bikehh.meldungen.dto.MeldungListDto;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.service.MeldungenService;

@Service
public class MeldungenApplicationService {

	private MeldungenService meldungenService;

	public MeldungenApplicationService(MeldungenService meldungenService) {
		this.meldungenService = meldungenService;
	}

	public List<MeldungListDto> getAlleMeldungen() {
		List<MeldungListDto> dtoList = new ArrayList<>();

		List<Meldung> meldungen = meldungenService.getAlleMeldungen();

		for (Meldung meldung : meldungen) {
			MeldungListDto dto = new MeldungListDto();
			dto.setId(meldung.getId());
			dto.setBefragungId(meldung.getBefragung().getId());
			dto.setErsteller(meldung.getErsteller().getEmailAddress());

			dto.setLaengengrad(meldung.getLaengengrad());
			dto.setBreitengrad(meldung.getBreitengrad());

			dto.setText(meldung.getText());
			dto.setKategorie(meldung.getKategorie());
			dto.setCreatedAt(meldung.getCreatedAt());

			dtoList.add(dto);
		}

		return dtoList;
	}

}
