package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenServiceTest {
	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private Umfrage umfrage;

	public UmfragenServiceTest() {
		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(umfrageRepository);

		// mocke Umfrage
		umfrage = mock(Umfrage.class);
	}

	@Test
	public void getAlleUmfragenTest() {
		List<Umfrage> umfragen = new ArrayList<Umfrage>();
		umfragen.add(umfrage);

		when(umfrageRepository.findAll()).thenReturn(umfragen);

		assertEquals(umfragen, umfragenService.getAlleUmfragen());
	}

	@Test
	public void getUmfrageNachIdTest() {
		when(umfrageRepository.findById((int) umfrage.getId())).thenReturn(Optional.of(umfrage));

		assertEquals(umfrage, umfragenService.getUmfrageNachId(umfrage.getId()));
	}

	@Test
	public void speichereOderUpdateUmfrage() {
		// TODO Test ausfüllen
		umfragenService.speichereOderUpdateUmfrage(umfrage);
	}

	@Test
	public void loescheTest() {
		// TODO Test ausfüllen
		umfragenService.loesche(umfrage.getId());
	}
}
