package de.wps.bikehh.umfragen.applicationservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.umfragen.dto.UmfrageDto;
import de.wps.bikehh.umfragen.service.UmfragenService;

@Service
public class UmfragenApplicationService {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenApplicationService(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	public UmfrageDto getUmfrageById(long umfragenId) {
		return UmfrageDto.from(umfragenService.getById(umfragenId));
	}

	public List<UmfrageDto> getAlleUmfragen() {
		return umfragenService.getAlleUmfragen().stream()
				.map(UmfrageDto::from)
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
