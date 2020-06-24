package de.wps.bikehh.adminplattform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.adminplattform.material.Umfrage;
import de.wps.bikehh.adminplattform.repository.UmfrageRepository;

@Service
public class UmfragenService {

	@Autowired
	UmfrageRepository umfrageRepository;

	public List<Umfrage> getAlleUmfragen() {
		List<Umfrage> umfragen = new ArrayList<Umfrage>();
		umfrageRepository.findAll().forEach(umfrage -> umfragen.add(umfrage));
		return umfragen;
	}

	public Umfrage getUmfrageNachId(int id) {
		return umfrageRepository.findById((int) id).get();
	}

	public int speichereOderUpdateUmfrage(Umfrage umfrage) {
		umfrageRepository.save(umfrage);
		return umfrage.getId();
	}

	public void loesche(int id) {
		umfrageRepository.deleteById((int) id);
	}
}
