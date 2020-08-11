package de.wps.bikehh.umfragen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfragenRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenServiceTest {

	private UmfragenRepository umfrageRepository;
	private UmfragenService umfragenService;
	private Umfrage testUmfrage;

	@Before
	public void init() {
		umfrageRepository = mock(UmfragenRepository.class);
		umfragenService = new UmfragenService(umfrageRepository);

		testUmfrage = new Umfrage();
		testUmfrage.setId(1);

		when(umfrageRepository.findById(testUmfrage.getId())).thenReturn(Optional.of(testUmfrage));
		when(umfrageRepository.existsById(testUmfrage.getId())).thenReturn(true);
	}

	@Test
	public void testgetUmfragenImUmkreis_gibtUmfragenIn50MeterUmkreis() {

	}

	@Test
	public void testbeantworteUmfrage_speichertAntwortZuUmfrage() {

	}

	@Test
	public void testenableUmfrage_aktiviertUmfrage() {
		// act
		umfragenService.enableUmfrage(testUmfrage.getId());

		// assert
		ArgumentCaptor<Umfrage> argument = ArgumentCaptor.forClass(Umfrage.class);
		verify(umfrageRepository).save(argument.capture());
		assertFalse(argument.getValue().getIstDisabled());
	}

	@Test
	public void testdisableUmfrage_deaktiviertUmfrage() {
		// act
		umfragenService.disableUmfrage(testUmfrage.getId());

		// assert
		ArgumentCaptor<Umfrage> argument = ArgumentCaptor.forClass(Umfrage.class);
		verify(umfrageRepository).save(argument.capture());
		assertTrue(argument.getValue().getIstDisabled());
	}
}
