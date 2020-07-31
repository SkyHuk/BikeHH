package de.wps.bikehh.meldungen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungenRepository;

/**
 * Service, der Operationen auf Meldungen behandelt.
 */
@Service
public class MeldungenService {

	private MeldungenRepository meldungenRepository;

	@Autowired
	public MeldungenService(MeldungenRepository meldungenRepository) {
		this.meldungenRepository = meldungenRepository;
	}

	/**
	 * Liefert alle in der Datenbank gespeicherten Meldungen
	 * 
	 * @return alle Meldungen
	 */
	public List<Meldung> getAlleMeldungen() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungenRepository.findAll().forEach(meldungen::add);
		return meldungen;
	}

	/**
	 * Liefert die Meldung zu einer gegebenen Id.
	 * 
	 * @param id
	 *            die id der Meldung
	 * 
	 * @require meldung exists
	 * 
	 * @return die Meldung zur gegebenen Id
	 */
	public Meldung getMeldungNachId(int id) {
		Contract.check(meldungenRepository.existsById((long) id), "meldung exists");

		return meldungenRepository.findById((long) id).get();
	}

	/**
	 * Speichert oder aktualisiert eine Meldung.
	 * 
	 * @param meldung
	 *            die zu speichernde / verändernde Meldung
	 * 
	 * @require meldung not null
	 */
	public void speichereOderUpdateMeldung(Meldung meldung) {
		Contract.notNull(meldung, "meldung");

		meldungenRepository.save(meldung);
	}

	/**
	 * Löscht eine Meldung aus der Datenbank.
	 * 
	 * @param id
	 *            id der zu löschenden Meldung
	 * 
	 * @require meldung exists
	 */
	public void loesche(int id) {
		Contract.check(meldungenRepository.existsById((long) id), "meldung exists");

		meldungenRepository.deleteById((long) id);
	}
}
