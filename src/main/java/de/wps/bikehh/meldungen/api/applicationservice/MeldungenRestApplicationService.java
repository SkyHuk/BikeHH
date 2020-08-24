package de.wps.bikehh.meldungen.api.applicationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.meldungen.api.dto.MeldungEinreichenRestDto;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.service.MeldungenService;

@Service
public class MeldungenRestApplicationService {

	private MeldungenService meldungenService;

	@Autowired
	public MeldungenRestApplicationService(MeldungenService meldungenService) {
		this.meldungenService = meldungenService;
	}

	public void reicheMeldungEin(User user, MeldungEinreichenRestDto dto) {
		Meldung meldung = new Meldung();
		meldung.setLaengengrad(dto.getLaengengrad());
		meldung.setBreitengrad(dto.getBreitengrad());
		meldung.setKategorie(dto.getKategorie());
		meldung.setText(dto.getText());
		meldung.setErsteller(user);

		meldungenService.reicheMeldungEin(user, meldung);
	}

}
