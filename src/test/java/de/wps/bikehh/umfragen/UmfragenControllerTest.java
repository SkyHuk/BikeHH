package de.wps.bikehh.umfragen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import de.wps.bikehh.umfragen.controller.UmfragenController;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenControllerTest {

	private UmfragenService umfragenService;
	private UmfragenController umfragenController;
	private Umfrage testUmfrage;
	private Model model;

	@Before
	public void init() {
		model = new ConcurrentModel();
		umfragenService = mock(UmfragenService.class);
		umfragenController = new UmfragenController(umfragenService);

		testUmfrage = new Umfrage();
		testUmfrage.setId(1);

		List<Umfrage> alleUmfragen = new ArrayList<>();
		alleUmfragen.add(testUmfrage);

		when(umfragenService.getUmfrageNachId(testUmfrage.getId())).thenReturn(testUmfrage);
		when(umfragenService.getAlleUmfragen()).thenReturn(alleUmfragen);
	}

	@Test
	public void testzeigeUmfragenListe_enthaeltUmfragenUndGibtTemplate() {
		// act
		String redirectStr = umfragenController.zeigeUmfragenListe(model);

		// assert
		verify(umfragenService, times(1)).getAlleUmfragen();
		assertTrue(model.containsAttribute("umfragen"));
		assertEquals("umfragen/umfragen_liste", redirectStr);
	}

	@Test
	public void testzeigeEinzelUmfrage_enthaeltUmfrageUndGibtTemplate() {
		// act
		String redirectStr = umfragenController.zeigeEinzelUmfrage(model, testUmfrage.getId());

		// assert
		verify(umfragenService, times(1)).getUmfrageNachId(testUmfrage.getId());
		assertTrue(model.containsAttribute("umfrage"));
		Umfrage displayedUmfrage = (Umfrage) model.getAttribute("umfrage");
		assertEquals(testUmfrage.getId(), displayedUmfrage.getId());
		assertEquals("umfragen/umfrage", redirectStr);
	}

}
