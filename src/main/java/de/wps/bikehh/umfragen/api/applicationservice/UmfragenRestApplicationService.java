package de.wps.bikehh.umfragen.api.applicationservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.umfragen.api.dto.UmfrageAntwortRestDto;
import de.wps.bikehh.umfragen.api.dto.UmfrageRestDto;
import de.wps.bikehh.umfragen.service.UmfragenService;

@Service
public class UmfragenRestApplicationService {

	private UmfragenService umfragenService;

	@Autowired
	public UmfragenRestApplicationService(UmfragenService umfragenService) {
		this.umfragenService = umfragenService;
	}

	public boolean hasUmfrage(long id) {
		return umfragenService.hasUmfrage(id);
	}

	public List<UmfrageRestDto> getUmfragenImUmkreis(double laengengrad, double breitengrad) {
		return umfragenService.getUmfragenImUmkreis(laengengrad, breitengrad).stream()
				.map(UmfrageRestDto::from)
				.collect(Collectors.toList());
	}

	public void beantworteUmfrage(UmfrageAntwortRestDto umfrageAntwort) {
		// TODO: Umfragen beantworten
		umfragenService.beantworteUmfrage();
	}
}
