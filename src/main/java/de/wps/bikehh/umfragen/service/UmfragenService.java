package de.wps.bikehh.umfragen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfragenRepository;

/**
 * Service, der Operationen auf Umfragen behandelt.
 */
@Service
public class UmfragenService {

	private UmfragenRepository umfrageRepository;

	@Autowired
	public UmfragenService(UmfragenRepository umfrageRepository) {
		this.umfrageRepository = umfrageRepository;
	}

	/**
	 * Speichert eine Umfrage und gibt die gespeicherte Umfrage zurück.
	 * 
	 * @param umfrage
	 *            die zu speichernde Umfrage
	 * 
	 * @require umfrage not null
	 */
	public Umfrage save(Umfrage umfrage) {
		Contract.notNull(umfrage, "umfrage");

		return umfrageRepository.save(umfrage);
	}

	/**
	 * Löscht eine Umfrage aus der Datenbank.
	 * 
	 * @param id
	 *            id der zu löschenden Umfrage
	 * 
	 * @require umfrage exists
	 */
	public void delete(long id) {
		Contract.check(umfrageRepository.existsById(id), "umfrage exists");

		umfrageRepository.deleteById(id);
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
	public Umfrage getById(long id) {
		Contract.check(umfrageRepository.existsById(id), "umfrage exists");

		return umfrageRepository.findById(id).get();
	}

	/***
	 * Überprüft, ob das Repository eine Umfrage mit gegebener ID enthält.
	 * 
	 * @param id
	 *            die id der Umfrage
	 * @return ob die Umfrage im Repository persistiert wird
	 */
	public boolean hasUmfrage(long id) {
		return umfrageRepository.existsById(id);
	}

	/**
	 * Liefert alle in der Datenbank gespeicherten Umfragen.
	 */
	public List<Umfrage> getAlleUmfragen() {
		List<Umfrage> umfragen = new ArrayList<>();
		umfrageRepository.findAll().forEach(umfragen::add);
		return umfragen;
	}

	public List<Umfrage> getUmfragenImUmkreis(double laengengrad, double breitengrad) {
		List<Umfrage> umfragen = new ArrayList<>();
		// TODO: Umfragen in einem bestimmten Radius um gegebener Position
		// raussuchen und bereitstellen.

		return umfragen;
	}

	public void beantworteUmfrage() {
		// TODO: Umfragen beantworten
	}

	public void enableUmfrage(long id) {
		Umfrage umfrage = getById(id);
		umfrage.setIstDisabled(false);
		save(umfrage);
	}

	public void disableUmfrage(long id) {
		Umfrage umfrage = getById(id);
		umfrage.setIstDisabled(true);
		save(umfrage);
	}

}
