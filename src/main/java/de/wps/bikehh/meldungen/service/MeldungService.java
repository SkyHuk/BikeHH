package de.wps.bikehh.meldungen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungRepository;

/**
 * Service für Datenbank
 * 
 * 
 * @author felixwolf
 *
 */
@Service
public class MeldungService {

	private MeldungRepository meldungRepository;

	@Autowired
	public MeldungService(MeldungRepository meldungRepository) {
		this.meldungRepository = meldungRepository;
	}

	/**
	 * liefert alle in der Datenbank gespeicherten Meldungen
	 * 
	 * @return alle Meldungen
	 */
	public List<Meldung> getAlleMeldungen() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungRepository.findAll().forEach(meldung -> meldungen.add(meldung));
		return meldungen;
	}

	/**
	 * liefert die zu einer Id passenden Meldung
	 * 
	 * @param id die id der Meldung
	 * @return die Meldung zur Id
	 */
	public Meldung getMeldungNachId(int id) {
		return meldungRepository.findById((long) id).get();
	}

	/**
	 * speichert oder updatet (wenn schon in DB vorhanden) eine Meldungen
	 * 
	 * @param meldung die zu speichernde / verändernde Meldung
	 */
	public void speichereOderUpdateMeldung(Meldung meldung) {
		meldungRepository.save(meldung);
	}

	/**
	 * löscht eine Meldung aus der Datenbank
	 * 
	 * @param id id der zu löschenden Meldung
	 */
	public void loesche(int id) {
		meldungRepository.deleteById((long) id);
	}
}
