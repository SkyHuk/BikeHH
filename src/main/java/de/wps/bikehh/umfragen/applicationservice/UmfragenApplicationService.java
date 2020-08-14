package de.wps.bikehh.umfragen.applicationservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.dto.EditBefragungDto;
import de.wps.bikehh.umfragen.dto.EditUmfrageDto;
import de.wps.bikehh.umfragen.dto.FrageDto;
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
		Umfrage umfrage = umfragenService.getById(umfragenId);

		ViewUmfrageDto dto = new ViewUmfrageDto();
		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setKategorie(umfrage.getKategorie());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setCreatedAt(umfrage.getCreatedAt());
		dto.setUpdatedAt(umfrage.getUpdatedAt());

		List<EditBefragungDto> befragungen = new ArrayList<>();
		for (Befragung befragung : umfrage.getBefragungen()) {
			EditBefragungDto befragungDto = new EditBefragungDto();
			befragungDto.setBefragungId(befragung.getId());
			befragungDto.setLaengengrad(befragung.getLaengengrad());
			befragungDto.setBreitengrad(befragung.getBreitengrad());
			befragungDto.setFahrtrichtung(befragung.getFahrtrichtung());

			List<FrageDto> fragen = new ArrayList<>();
			for (Frage frage : befragung.getFragen()) {
				FrageDto frageDto = new FrageDto();
				frageDto.setText(frage.getText());
				frageDto.setAntworten(frage.getAntworten());
				frageDto.setHatFreitextAntwort(frage.getHatFreitextAntwort());

				fragen.add(frageDto);
			}
			befragungDto.setFragen(fragen);

			befragungen.add(befragungDto);
		}
		dto.setBefragungen(befragungen);

		dto.setErsteller(umfrage.getErsteller().getEmailAddress());
		dto.setIstMehrfachBeantwortbar(umfrage.getIstMehrfachBeantwortbar());
		dto.setIstDisabled(umfrage.getIstDisabled());
		return dto;
	}

	public EditUmfrageDto getUmfrageForEdit(long umfragenId) {
		Umfrage umfrage = umfragenService.getById(umfragenId);
		EditUmfrageDto dto = new EditUmfrageDto();

		dto.setId(umfrage.getId());
		dto.setTitel(umfrage.getTitel());
		dto.setStartDatum(umfrage.getStartDatum());
		dto.setEndDatum(umfrage.getEndDatum());
		dto.setIstMehrfachBeantwortbar(umfrage.getIstMehrfachBeantwortbar());
		dto.setKategorie(umfrage.getKategorie());

		List<EditBefragungDto> befragungen = new ArrayList<>();
		for (Befragung befragung : umfrage.getBefragungen()) {
			EditBefragungDto befragungDto = new EditBefragungDto();
			befragungDto.setBefragungId(befragung.getId());
			befragungDto.setLaengengrad(befragung.getLaengengrad());
			befragungDto.setBreitengrad(befragung.getBreitengrad());
			befragungDto.setFahrtrichtung(befragung.getFahrtrichtung());

			List<FrageDto> fragen = new ArrayList<>();
			for (Frage frage : befragung.getFragen()) {
				FrageDto frageDto = new FrageDto();
				frageDto.setText(frage.getText());
				frageDto.setAntworten(frage.getAntworten());
				frageDto.setHatFreitextAntwort(frage.getHatFreitextAntwort());

				fragen.add(frageDto);
			}
			befragungDto.setFragen(fragen);

			befragungen.add(befragungDto);
		}
		dto.setBefragungen(befragungen);

		return dto;
	}

	public List<ViewUmfrageDto> getAlleUmfragenFuerKarte() {
		// TODO: Neues Dto für die große Kartenansicht
		return new ArrayList<>();
	}

	public List<UmfragenListeDto> getUmfragenUebersichtsListe() {
		List<UmfragenListeDto> dtoListe = new ArrayList<>();

		List<Umfrage> alleUmfragen = umfragenService.getAlleUmfragen();
		for (Umfrage umfrage : alleUmfragen) {
			UmfragenListeDto dto = new UmfragenListeDto();
			dto.setId(umfrage.getId());
			dto.setTitel(umfrage.getTitel());
			dto.setKategorie(umfrage.getKategorie());
			dto.setCreatedAt(umfrage.getCreatedAt().toString());
			dto.setIsDisabled(umfrage.getIstDisabled());

			dtoListe.add(dto);
		}

		return dtoListe;
	}

	public Long saveNewUmfrage(User ersteller, NewUmfrageDto dto) {
		Umfrage umfrage = new Umfrage();
		umfrage.setTitel(dto.getTitel());
		umfrage.setStartDatum(dto.getStartDatum());
		umfrage.setEndDatum(dto.getEndDatum());
		umfrage.setCreatedAt(LocalDate.now());
		umfrage.setKategorie(dto.getKategorie());
		umfrage.setIstMehrfachBeantwortbar(dto.getIstMehrfachBeantwortbar());
		umfrage.setErsteller(ersteller);

		List<Befragung> befragungen = new ArrayList<>();
		for (EditBefragungDto befragungDto : dto.getBefragungen()) {
			Befragung befragung = new Befragung();
			befragung.setUmfrage(umfrage);
			befragung.setLaengengrad(befragungDto.getLaengengrad());
			befragung.setBreitengrad(befragungDto.getBreitengrad());
			befragung.setFahrtrichtung(befragungDto.getFahrtrichtung());
			befragung.setStartDatum(dto.getStartDatum());
			befragung.setEndDatum(dto.getEndDatum());
			befragung.setErsteller(ersteller);

			List<Frage> fragen = new ArrayList<>();
			for (FrageDto frageDto : befragungDto.getFragen()) {
				Frage frage = new Frage();
				frage.setBefragung(befragung);
				frage.setText(frageDto.getText());
				frage.setAntworten(frageDto.getAntworten());

				fragen.add(frage);
			}
			befragung.setFragen(fragen);

			befragungen.add(befragung);
		}
		umfrage.setBefragungen(befragungen);

		Umfrage savedUmfrage = umfragenService.save(umfrage);
		return savedUmfrage.getId();
	}

	public long saveEditedUmfrage(EditUmfrageDto dto) {
		Umfrage umfrage = umfragenService.getById(dto.getId());

		umfrage.setTitel(dto.getTitel());
		umfrage.setStartDatum(dto.getStartDatum());
		umfrage.setEndDatum(dto.getEndDatum());
		umfrage.setIstMehrfachBeantwortbar(dto.getIstMehrfachBeantwortbar());
		umfrage.setKategorie(dto.getKategorie());

		List<Befragung> befragungen = umfrage.getBefragungen();
		befragungen.clear();
		for (EditBefragungDto befragungDto : dto.getBefragungen()) {
			Befragung befragung = new Befragung();
			befragung.setUmfrage(umfrage);
			befragung.setLaengengrad(befragungDto.getLaengengrad());
			befragung.setBreitengrad(befragungDto.getBreitengrad());
			befragung.setFahrtrichtung(befragungDto.getFahrtrichtung());
			befragung.setStartDatum(dto.getStartDatum());
			befragung.setEndDatum(dto.getEndDatum());
			befragung.setErsteller(umfrage.getErsteller());

			List<Frage> fragen = new ArrayList<>();
			for (FrageDto frageDto : befragungDto.getFragen()) {
				Frage frage = new Frage();
				frage.setBefragung(befragung);
				frage.setText(frageDto.getText());
				frage.setAntworten(frageDto.getAntworten());

				fragen.add(frage);
			}
			befragung.setFragen(fragen);
			befragungen.add(befragung);
		}
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

}
