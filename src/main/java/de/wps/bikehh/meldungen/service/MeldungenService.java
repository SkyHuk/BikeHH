package de.wps.bikehh.meldungen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungenRepository;

/**
 * Service, der Operationen auf Meldungen behandelt.
 * 
 * @author felixwolf
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
	 * Liefert die zu einer Id passenden Meldung
	 * 
	 * @param id
	 *            die id der Meldung
	 * 
	 * @return die Meldung zur Id
	 */
	public Meldung getMeldungNachId(int id) {
		return meldungenRepository.findById((long) id).get();
	}

	/**
	 * Speichert oder aktualisiert eine Meldungen
	 * 
	 * @param meldung
	 *            die zu speichernde / verändernde Meldung
	 */
	public void speichereOderUpdateMeldung(Meldung meldung) {
		meldungenRepository.save(meldung);
	}

	/**
	 * Löscht eine Meldung aus der Datenbank
	 * 
	 * @param id
	 *            id der zu löschenden Meldung
	 */
	public void loesche(int id) {
		meldungenRepository.deleteById((long) id);
	}
}
