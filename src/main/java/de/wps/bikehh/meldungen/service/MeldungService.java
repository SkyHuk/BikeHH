package de.wps.bikehh.meldungen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungRepository;

@Service
public class MeldungService {

	@Autowired
	private MeldungRepository meldungRepository;

	public MeldungService(MeldungRepository meldungRepository) {
		this.meldungRepository = meldungRepository;
	}

	public List<Meldung> getAlleMeldungen() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungRepository.findAll().forEach(meldung -> meldungen.add(meldung));
		return meldungen;
	}

	public Meldung getMeldungNachId(int id) {
		return meldungRepository.findById((long) id).get();
	}

	public void speichereOderUpdateMeldung(Meldung meldung) {
		meldungRepository.save(meldung);
	}

	public void loesche(int id) {
		meldungRepository.deleteById((long) id);
	}
}
