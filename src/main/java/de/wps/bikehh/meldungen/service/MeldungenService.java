package de.wps.bikehh.meldungen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.befragungen.service.BefragungenService;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungenRepository;

/**
 * Service, der Operationen auf Meldungen behandelt.
 */
@Service
public class MeldungenService {

	private MeldungenRepository meldungenRepository;

	private BefragungenService befragungenService;

	@Autowired
	public MeldungenService(MeldungenRepository meldungenRepository,
			BefragungenService befragungenService) {
		this.meldungenRepository = meldungenRepository;
		this.befragungenService = befragungenService;
	}

	public Meldung save(Meldung meldung) {
		Contract.notNull(meldung, "meldung");

		return meldungenRepository.save(meldung);
	}

	public boolean hasMeldung(long id) {
		return meldungenRepository.existsById(id);
	}

	public void delete(long id) {
		Contract.check(hasMeldung(id), "hasMeldung(id)");

		meldungenRepository.deleteById(id);
	}

	public void reicheMeldungEin(User user, Meldung meldung) {
		Contract.notNull(user, "user");
		Contract.notNull(meldung, "meldung");

		Meldung savedMeldung = save(meldung);
		befragungenService.createNewBefragungFromMeldung(user, savedMeldung);
	}

	public List<Meldung> getAlleMeldungen() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungenRepository.findAll().forEach(meldungen::add);
		return meldungen;
	}

	public Meldung getById(int id) {
		Contract.check(meldungenRepository.existsById((long) id), "meldung exists");

		return meldungenRepository.findById((long) id).get();
	}

}
