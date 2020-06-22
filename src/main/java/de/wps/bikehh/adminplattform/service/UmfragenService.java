package de.wps.bikehh.adminplattform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.wps.bikehh.adminplattform.material.UmfrageDB;
import de.wps.bikehh.adminplattform.repository.UmfrageRepository;

public class UmfragenService {

	@Autowired
	UmfrageRepository surveyRepository;

	public List<UmfrageDB> getAlleUmfragen() {
		List<UmfrageDB> surveys = new ArrayList<UmfrageDB>();
		surveyRepository.findAll().forEach(survey -> surveys.add(survey));
		return surveys;
	}

	public UmfrageDB getUmfrageNachId(int id) {
		return surveyRepository.findById((long) id).get();
	}

	public void speichereOderUpdateUmfrage(UmfrageDB survey) {
		surveyRepository.save(survey);
	}

	public void loesche(int id) {
		surveyRepository.deleteById((long) id);
	}
}
