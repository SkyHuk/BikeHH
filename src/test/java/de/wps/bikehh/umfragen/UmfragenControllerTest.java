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
import org.springframework.validation.BindingResult;

import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;
import de.wps.bikehh.umfragen.controller.UmfragenController;
import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;

public class UmfragenControllerTest {

	private UmfragenApplicationService umfragenAppService;
	private UmfragenController umfragenController;
	private ViewUmfrageDto testUmfrage;
	private Model model;
	private BindingResult bindingResult;

	@Before
	public void init() {
		model = new ConcurrentModel();
		umfragenAppService = mock(UmfragenApplicationService.class);
		umfragenController = new UmfragenController(umfragenAppService);

		testUmfrage = new ViewUmfrageDto();
		testUmfrage.setId(1);

		List<ViewUmfrageDto> alleUmfragen = new ArrayList<>();
		alleUmfragen.add(testUmfrage);

		bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		when(umfragenAppService.hasUmfrage(testUmfrage.getId())).thenReturn(true);
		when(umfragenAppService.getUmfrageById(testUmfrage.getId())).thenReturn(testUmfrage);
		when(umfragenAppService.getAlleUmfragenFuerKarte()).thenReturn(alleUmfragen);
	}

	@Test
	public void testzeigeUmfragenListe_enthaeltUmfragenUndGibtTemplate() {
		// act
		String redirectStr = umfragenController.zeigeUmfragenListe(model);

		// assert
		verify(umfragenAppService, times(1)).getUmfragenUebersichtsListe();
		assertTrue(model.containsAttribute("umfragen"));
		assertEquals("umfragen/umfragen_liste", redirectStr);
	}

	@Test
	public void testzeigeEinzelUmfrage_enthaeltUmfrageUndGibtTemplate() {
		// act
		String redirectStr = umfragenController.zeigeEinzelUmfrage(model, testUmfrage.getId());

		// assert
		verify(umfragenAppService, times(1)).getUmfrageById(testUmfrage.getId());
		assertTrue(model.containsAttribute("umfrage"));
		ViewUmfrageDto displayedUmfrage = (ViewUmfrageDto) model.getAttribute("umfrage");
		assertEquals(testUmfrage.getId(), displayedUmfrage.getId());
		assertEquals("umfragen/umfrage", redirectStr);
	}

	@Test
	public void testdeaktiviereUmfrage_deaktiviertUmfrage() {
		// act
		umfragenController.deaktiviereUmfrage(testUmfrage.getId());

		// assert
		verify(umfragenAppService, times(1)).disableUmfrage(testUmfrage.getId());
	}

	@Test
	public void testaktiviereUmfrage_aktiviertUmfrage() {
		// act
		umfragenController.aktiviereUmfrage(testUmfrage.getId());

		// assert
		verify(umfragenAppService, times(1)).enableUmfrage(testUmfrage.getId());
	}

}
