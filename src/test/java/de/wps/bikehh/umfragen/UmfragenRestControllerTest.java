package de.wps.bikehh.umfragen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import de.wps.bikehh.umfragen.api.controller.UmfragenRestController;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenRestControllerTest {

	private UmfragenService umfragenService;
	private UmfragenRestController umfragenRestController;
	private Umfrage testUmfrage;

	@Before
	public void init() {
		umfragenService = mock(UmfragenService.class);
		umfragenRestController = new UmfragenRestController(umfragenService);

		testUmfrage = new Umfrage();
		testUmfrage.setId(1);

		when(umfragenService.getById(testUmfrage.getId())).thenReturn(testUmfrage);
	}

	@Test
	public void testloescheUmfrage_loeschtUmfrage() {
		// act
		umfragenRestController.loescheUmfrage(testUmfrage.getId());

		// assert
		verify(umfragenService, times(1)).delete(testUmfrage.getId());
	}

	@Test
	public void testdeaktiviereUmfrage_deaktiviertUmfrage() {
		// arrange
		testUmfrage.setUmfrageDisabled(false);

		// act
		umfragenRestController.deaktiviereUmfrage(testUmfrage.getId());

		// assert
		verify(umfragenService, times(1)).save(testUmfrage);
		assertTrue(testUmfrage.isUmfrageDisabled());
	}

	@Test
	public void testaktiviereUmfrage_aktiviertUmfrage() {
		// arrange
		testUmfrage.setUmfrageDisabled(true);

		// act
		umfragenRestController.aktiviereUmfrage(testUmfrage.getId());

		// assert
		verify(umfragenService, times(1)).save(testUmfrage);
		assertFalse(testUmfrage.isUmfrageDisabled());
	}

}
