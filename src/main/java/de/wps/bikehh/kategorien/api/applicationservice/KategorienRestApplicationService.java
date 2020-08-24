package de.wps.bikehh.kategorien.api.applicationservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.kategorien.api.dto.KategorieRestDto;
import de.wps.bikehh.kategorien.material.Kategorie;
import de.wps.bikehh.kategorien.service.KategorienService;

@Service
public class KategorienRestApplicationService {

	private KategorienService kategorienService;

	@Autowired
	public KategorienRestApplicationService(KategorienService kategorienService) {
		this.kategorienService = kategorienService;
	}

	public List<KategorieRestDto> getAlleKategorien() {
		List<KategorieRestDto> dtoList = new ArrayList<>();

		List<Kategorie> alleKategorien = kategorienService.getAlleKategorien();
		for (Kategorie kategorie : alleKategorien) {
			dtoList.add(convertKatergorieToDto(kategorie));
		}

		return dtoList;
	}

	/**
	 * Baut rekursiv ein KategorieRestDto mit seinen Unterkategorien.
	 * 
	 * @param kategorie
	 *            Die Kategorie aus der Datenbank
	 */
	private KategorieRestDto convertKatergorieToDto(Kategorie kategorie) {
		KategorieRestDto dto = new KategorieRestDto();
		dto.setId(kategorie.getId());
		dto.setName(kategorie.getName());

		List<KategorieRestDto> unterKategorien = kategorie.getUnterKategorien()
				.stream()
				.map(k -> convertKatergorieToDto(k))
				.collect(Collectors.toList());

		dto.setUnterKategorien(unterKategorien);
		return dto;
	}

}
