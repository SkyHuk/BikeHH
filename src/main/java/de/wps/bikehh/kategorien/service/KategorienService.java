package de.wps.bikehh.kategorien.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import de.wps.bikehh.kategorien.material.Kategorie;
import de.wps.bikehh.kategorien.repository.KategorienRepository;

@Service
public class KategorienService {

	private KategorienRepository kategorienRepository;

	public KategorienService(KategorienRepository kategorienRepository) {
		this.kategorienRepository = kategorienRepository;
	}

	public List<Kategorie> getAlleKategorien() {
		List<Kategorie> kategorien = new ArrayList<>();
		kategorienRepository.findAll().forEach(kategorien::add);
		return kategorien;
	}

}
