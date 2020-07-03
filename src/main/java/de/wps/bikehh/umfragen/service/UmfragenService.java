package de.wps.bikehh.umfragen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;

/**
 * Service für Datenbank-Funktionen
 */
@Service
public class UmfragenService {

	private UmfrageRepository umfrageRepository;

	@Autowired
	public UmfragenService(UmfrageRepository umfrageRepository) {
		this.umfrageRepository = umfrageRepository;
	}

	/**
	 * liefert alle in der Datenbank gespeicherten Umfragen
	 * 
	 * @return alle Umfragen
	 */
	public List<Umfrage> getAlleUmfragen() {
		List<Umfrage> umfragen = new ArrayList<Umfrage>();
		umfrageRepository.findAll().forEach(umfrage -> umfragen.add(umfrage));
		return umfragen;
	}

	/**
	 * liefert die zu einer Id passenden Umfrage
	 * 
	 * @param id die id der Umfrage
	 * @return die Umfrage zur Id
	 */
	public Umfrage getUmfrageNachId(int id) {
		return umfrageRepository.findById((int) id).get();
	}

	/**
	 * speichert oder updatet (wenn schon in DB vorhanden) eine Umfrage
	 * 
	 * @param umfrage die zu speichernde / verändernde Umfrage
	 */
	public int speichereOderUpdateUmfrage(Umfrage umfrage) {
		umfrageRepository.save(umfrage);
		return umfrage.getId();
	}

	/**
	 * löscht eine Umfrage aus der Datenbank
	 * 
	 * @param id id der zu löschenden Umfrage
	 */
	public void loesche(int id) {
		umfrageRepository.deleteById((int) id);
	}
}
