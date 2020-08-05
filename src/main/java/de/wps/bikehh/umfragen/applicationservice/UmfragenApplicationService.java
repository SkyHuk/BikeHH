package de.wps.bikehh.umfragen.applicationservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;
import de.wps.bikehh.umfragen.dto.UmfragenListeDto;
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
