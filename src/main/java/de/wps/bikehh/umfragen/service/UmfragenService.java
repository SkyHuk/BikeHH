package de.wps.bikehh.umfragen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;

/**
 * Service, der Operationen auf Umfragen behandelt.
 */
@Service
public class UmfragenService {

	private UmfrageRepository umfrageRepository;

	@Autowired
	public UmfragenService(UmfrageRepository umfrageRepository) {
		this.umfrageRepository = umfrageRepository;
	}

	/**
	 * Liefert alle in der Datenbank gespeicherten Umfragen.
	 */
	public List<Umfrage> getAlleUmfragen() {
		List<Umfrage> umfragen = new ArrayList<>();
		umfrageRepository.findAll().forEach(umfragen::add);
		return umfragen;
	}

	/**
	 * Liefert die Umfrage zu einer gegebenen Id.
	 * 
	 * @param id
	 *            die id der Umfrage
	 * 
	 * @require umfrage exists
	 * 
	 * @return die Umfrage zur gegebenen Id
	 */
	public Umfrage getUmfrageNachId(long id) {
		Contract.check(umfrageRepository.existsById(id), "umfrage exists");

		return umfrageRepository.findById(id).get();
	}

	/**
	 * Speichert oder aktualisiert eine Umfrage.
	 * 
	 * @param umfrage
	 *            die zu speichernde / verändernde Umfrage
	 * 
	 * @require umfrage not null
	 */
	public int speichereOderUpdateUmfrage(Umfrage umfrage) {
		Contract.notNull(umfrage, "umfrage");

		umfrageRepository.save(umfrage);
		return umfrage.getId();
	}

	/**
	 * Löscht eine Umfrage aus der Datenbank.
	 * 
	 * @param id
	 *            id der zu löschenden Umfrage
	 * 
	 * @require umfrage exists
	 */
	public void loesche(long id) {
		Contract.check(umfrageRepository.existsById(id), "umfrage exists");

		umfrageRepository.deleteById(id);
	}
}
