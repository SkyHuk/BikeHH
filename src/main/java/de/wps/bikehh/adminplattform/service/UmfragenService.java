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
		List<UmfrageDB> surveys = new ArrayList<UmfrageDB>();
		umfrageRepository.findAll().forEach(survey -> surveys.add(survey));
		return surveys;
	}

	public UmfrageDB getUmfrageNachId(int id) {
		return umfrageRepository.findById((long) id).get();
	}

	public void speichereOderUpdateUmfrage(UmfrageDB survey) {
		umfrageRepository.save(survey);
	}

	public void loesche(int id) {
		umfrageRepository.deleteById((long) id);
	}
}
