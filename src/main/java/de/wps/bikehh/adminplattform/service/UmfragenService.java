package de.wps.bikehh.adminplattform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.wps.bikehh.adminplattform.material.UmfrageDB;
import de.wps.bikehh.adminplattform.repository.UmfrageRepository;

public class UmfragenService {

	@Autowired
	UmfrageRepository umfrageRepository;

	public List<UmfrageDB> getAlleUmfragen() {
		List<UmfrageDB> umfragen = new ArrayList<UmfrageDB>();
		umfrageRepository.findAll().forEach(umfrage -> umfragen.add(umfrage));
		return umfragen;
	}

	public UmfrageDB getUmfrageNachId(int id) {
		return umfrageRepository.findById((long) id).get();
	}

	public void speichereOderUpdateUmfrage(UmfrageDB umfrage) {
		umfrageRepository.save(umfrage);
	}

	public void loesche(int id) {
		umfrageRepository.deleteById((long) id);
	}
}
