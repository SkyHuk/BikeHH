package de.wps.bikehh.umfragen.applicationservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.dto.EditUmfrageDto;
import de.wps.bikehh.umfragen.dto.FrageDto;
import de.wps.bikehh.umfragen.dto.NewUmfrageDto;
import de.wps.bikehh.umfragen.dto.UmfragenListeDto;
import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;
import de.wps.bikehh.umfragen.fachwert.Kategorie;
import de.wps.bikehh.umfragen.material.Frage;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

@Service
public class UmfragenApplicationService {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenApplicationService(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	public ViewUmfrageDto getUmfrageById(long umfragenId) {
		return ViewUmfrageDto.from(umfragenService.getById(umfragenId));
	}

	public EditUmfrageDto getUmfrageForEdit(long umfragenId) {
		return EditUmfrageDto.from(umfragenService.getById(umfragenId));
	}

	public List<ViewUmfrageDto> getAlleUmfragen() {
		return umfragenService.getAlleUmfragen().stream()
				.map(ViewUmfrageDto::from)
				.collect(Collectors.toList());
	}

	public List<UmfragenListeDto> getUmfragenUebersichtsListe() {
		return umfragenService.getAlleUmfragen().stream()
				.map(UmfragenListeDto::from)
				.collect(Collectors.toList());
	}

	public long addNewUmfrage(User ersteller, NewUmfrageDto umfrageDto, boolean isManuellErstellt) {
		Umfrage umfrage = createUmfrageFromDto(umfrageDto);
		umfrage.setErsteller(ersteller);
		umfrage.setManuellErstellt(isManuellErstellt);
		Umfrage savedUmfrage = umfragenService.add(umfrage);
		return savedUmfrage.getId();
	}

	public void saveEditedUmfrage(EditUmfrageDto umfrageDto) {
		// TODO: Material aus Dto generieren und Umfrage speichern
	}

	public void enableUmfrage(long umfragenId) {
		umfragenService.enableUmfrage(umfragenId);
	}

	public void disableUmfrage(long umfragenId) {
		umfragenService.disableUmfrage(umfragenId);
	}

	public boolean hasUmfrage(long id) {
		return umfragenService.hasUmfrage(id);
	}

	private Umfrage createUmfrageFromDto(NewUmfrageDto dto) {
		Umfrage umfrage = new Umfrage();
		umfrage.setTitel(dto.getTitel());
		umfrage.setCreatedAt(LocalDate.now());
		umfrage.setUpdatedAt(LocalDate.now());
		umfrage.setBestaetigtSchwellenwert(dto.getBestaetigungsSchwellenwert());
		umfrage.setLaengengrad(dto.getLaengengrad());
		umfrage.setBreitengrad(dto.getBreitengrad());
		umfrage.setStartDatum(dto.getStartDatum());
		umfrage.setEndDatum(dto.getEndDatum());

		// TODO: Kategorie embedabble machen
		Kategorie kategorie = new Kategorie();
		kategorie.setName(dto.getKategorie());
		umfrage.setKategorie(kategorie);

		List<Frage> fragen = new ArrayList<>();
		for (FrageDto frage : dto.getFragen()) {
			Frage frageMat = new Frage();
			frageMat.setTitel(frage.getTitel());
			frageMat.setAntworten(frage.getAntworten());
			// TODO: Freitextantworten
			fragen.add(frageMat);
		}
		umfrage.setFragen(fragen);

		return umfrage;
	}

}
