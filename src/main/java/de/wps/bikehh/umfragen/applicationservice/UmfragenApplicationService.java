package de.wps.bikehh.umfragen.applicationservice;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.dto.EditUmfrageDto;
import de.wps.bikehh.umfragen.dto.NewUmfrageDto;
import de.wps.bikehh.umfragen.dto.UmfragenListeDto;
import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;
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

	public long addNewUmfrage(User ersteller, NewUmfrageDto dto) {
		Umfrage umfrage = new Umfrage();
		fillUmfrageFromDto(umfrage, dto);

		umfrage.setErsteller(ersteller);
		umfrage.setCreatedAt(LocalDate.now());

		Umfrage savedUmfrage = umfragenService.save(umfrage);
		return savedUmfrage.getId();
	}

	public long saveEditedUmfrage(EditUmfrageDto dto) {
		Umfrage umfrage = umfragenService.getById(dto.getId());
		fillUmfrageFromDto(umfrage, dto);

		umfrage.setUpdatedAt(LocalDate.now());

		Umfrage savedUmfrage = umfragenService.save(umfrage);
		return savedUmfrage.getId();
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

	private void fillUmfrageFromDto(Umfrage umfrage, NewUmfrageDto dto) {
		umfrage.setTitel(dto.getTitel());
		umfrage.setStartDatum(dto.getStartDatum());
		umfrage.setEndDatum(dto.getEndDatum());
		umfrage.setKategorie(dto.getKategorie());
	}

}
