package de.wps.bikehh.kategorien.applicationservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.kategorien.dto.KategorieListDto;
import de.wps.bikehh.kategorien.dto.NewUnterkategorieDto;
import de.wps.bikehh.kategorien.material.Kategorie;
import de.wps.bikehh.kategorien.service.KategorienService;

@Service
public class KategorienApplicationService {

	private KategorienService kategorienService;

	@Autowired
	public KategorienApplicationService(KategorienService kategorienService) {
		this.kategorienService = kategorienService;
	}

	public List<KategorieListDto> getAlleKategorien() {
		List<KategorieListDto> dtoList = new ArrayList<>();

		List<Kategorie> alleKategorien = kategorienService.getAlleKategorien();
		for (Kategorie kategorie : alleKategorien) {
			dtoList.add(convertKatergorieToDto(kategorie));
		}

		return dtoList;
	}

	public void addNewOberkategorie(String name) {
		kategorienService.addKategorie(name);
	}

	public NewUnterkategorieDto createUnterkategorieDto(long oberKategorieId) {
		Kategorie oberkategorie = kategorienService.getById(oberKategorieId);

		NewUnterkategorieDto dto = new NewUnterkategorieDto();
		dto.setOberKategorieId(oberKategorieId);
		dto.setOberKategorieName(oberkategorie.getName());
		return dto;
	}

	public void addNewUnterKategorie(NewUnterkategorieDto dto) {
		kategorienService.addKategorie(dto.getName(), dto.getOberKategorieId());
	}

	public boolean exists(long kategorieId) {
		return kategorienService.existsById(kategorieId);
	}

	public void delete(long kategorieId) {
		kategorienService.delete(kategorieId);
	}

	/**
	 * Baut ein KategorieListDto mit seinen Unterkategorien rekursiv.
	 * 
	 * @param kategorie
	 *            Die Kategorie aus der Datenbank
	 */
	private KategorieListDto convertKatergorieToDto(Kategorie kategorie) {
		KategorieListDto dto = new KategorieListDto();
		dto.setId(kategorie.getId());
		dto.setName(kategorie.getName());

		List<KategorieListDto> unterKategorien = kategorie.getUnterKategorien()
				.stream()
				.map(k -> convertKatergorieToDto(k))
				.collect(Collectors.toList());

		dto.setUnterKategorien(unterKategorien);
		return dto;
	}

}
