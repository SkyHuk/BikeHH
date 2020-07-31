package de.wps.bikehh.umfragen;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenServiceTest {

	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private Umfrage testUmfrage;
	private List<Umfrage> umfragen;

	@Before
	public void init() {
		umfrageRepository = mock(UmfrageRepository.class);
		umfragenService = new UmfragenService(umfrageRepository);

		testUmfrage = new Umfrage();
		testUmfrage.setId(1);

		Umfrage testUmfrage2 = new Umfrage();
		testUmfrage2.setId(2);

		umfragen = new ArrayList<>();
		umfragen.add(testUmfrage);
		umfragen.add(testUmfrage2);

		when(umfrageRepository.findById(testUmfrage.getId())).thenReturn(Optional.of(testUmfrage));
		when(umfrageRepository.findAll()).thenReturn(umfragen);
		when(umfrageRepository.existsById(anyInt())).thenReturn(true);
	}

	@Test
	public void getAlleUmfragenTest() {
		// act
		umfragenService.getAlleUmfragen();

		// assert
		verify(umfrageRepository, times(1)).findAll();
		assertEquals(2, umfragenService.getAlleUmfragen().size());
	}

	@Test
	public void getUmfrageNachIdTest() {
		// act
		umfragenService.getUmfrageNachId(testUmfrage.getId());

		// assert
		verify(umfrageRepository, times(1)).findById(testUmfrage.getId());
		assertEquals(testUmfrage, umfragenService.getUmfrageNachId(testUmfrage.getId()));
	}

	@Test
	public void speichereOderUpdateUmfrage() {
		// act
		umfragenService.speichereOderUpdateUmfrage(testUmfrage);

		// assert
		verify(umfrageRepository, times(1)).save(testUmfrage);
	}

	@Test
	public void loescheTest() {
		// act
		umfragenService.loesche(testUmfrage.getId());

		// assert
		verify(umfrageRepository, times(1)).deleteById(testUmfrage.getId());
	}
}
