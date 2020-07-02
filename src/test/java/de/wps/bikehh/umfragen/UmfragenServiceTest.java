package de.wps.bikehh.umfragen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
		umfragenService.getAlleUmfragen();
		Mockito.verify(umfrageRepository, times(1)).findAll();

		assertEquals(umfragen, umfragenService.getAlleUmfragen());
	}

	@Test
	public void getUmfrageNachIdTest() {
		when(umfrageRepository.findById((int) umfrage.getId())).thenReturn(Optional.of(umfrage));

		umfragenService.getUmfrageNachId(umfrage.getId());
		Mockito.verify(umfrageRepository, times(1)).findById(umfrage.getId());

		assertEquals(umfrage, umfragenService.getUmfrageNachId(umfrage.getId()));
	}

	@Test
	public void speichereOderUpdateUmfrage() {
		umfragenService.speichereOderUpdateUmfrage(umfrage);
		Mockito.verify(umfrageRepository, times(1)).save(umfrage);
	}

	@Test
	public void loescheTest() {
		umfragenService.loesche(umfrage.getId());
		Mockito.verify(umfrageRepository, times(1)).deleteById(umfrage.getId());
	}
}
