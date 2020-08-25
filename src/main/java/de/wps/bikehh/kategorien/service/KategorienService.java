package de.wps.bikehh.kategorien.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.kategorien.material.Kategorie;
import de.wps.bikehh.kategorien.repository.KategorienRepository;

@Service
public class KategorienService {

	private KategorienRepository kategorienRepository;

	public KategorienService(KategorienRepository kategorienRepository) {
		this.kategorienRepository = kategorienRepository;
	}

	public Kategorie addKategorie(String name) {
		Contract.notEmpty(name, "name");

		Kategorie kategorie = new Kategorie();
		kategorie.setName(name);
		return kategorienRepository.save(kategorie);
	}

	public Kategorie addKategorie(String name, long oberkategorieId) {
		Contract.notEmpty(name, "name");
		Contract.check(existsById(oberkategorieId), "existsById(oberkategorieId)");

		Kategorie oberKategorie = getById(oberkategorieId);

		Kategorie kategorie = new Kategorie();
		kategorie.setName(name);
		kategorie.setOberKategorie(oberKategorie);
		return kategorienRepository.save(kategorie);
	}

	public boolean isEmpty() {
		return kategorienRepository.count() == 0;
	}

	public Kategorie getById(long id) {
		Contract.check(existsById(id), "existsById(id)");

		return kategorienRepository.findById(id).get();
	}

	public boolean existsById(long id) {
		return kategorienRepository.existsById(id);
	}

	public void delete(long id) {
		Contract.check(existsById(id), "existsById(id)");

		kategorienRepository.deleteById(id);
	}

	public List<Kategorie> getAlleKategorien() {
		List<Kategorie> kategorien = new ArrayList<>();
		kategorienRepository.findAll().forEach(kategorie -> {
			if (kategorie.isOberkategorie()) {
				kategorien.add(kategorie);
			}
		});
		return kategorien;
	}

}
