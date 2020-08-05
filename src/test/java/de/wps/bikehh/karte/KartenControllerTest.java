package de.wps.bikehh.karte;

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

import de.wps.bikehh.karte.controller.KartenController;
import de.wps.bikehh.umfragen.applicationservice.UmfragenApplicationService;
import de.wps.bikehh.umfragen.dto.ViewUmfrageDto;

public class KartenControllerTest {

	private UmfragenApplicationService umfragenAppService;
	private KartenController kartenController;
	private Model model;

	@Before
	public void init() {
		model = new ConcurrentModel();
		umfragenAppService = mock(UmfragenApplicationService.class);
		kartenController = new KartenController(umfragenAppService);

		ViewUmfrageDto testUmfrage = new ViewUmfrageDto();
		testUmfrage.setId(1);

		List<ViewUmfrageDto> alleUmfragen = new ArrayList<>();
		alleUmfragen.add(testUmfrage);

		when(umfragenAppService.getAlleUmfragen()).thenReturn(alleUmfragen);
	}

	@Test
	public void testgetMap_enthaeltUmfragenUndGibtTemplate() {
		// act
		String redirectStr = kartenController.getMap(model);

		// assert
		verify(umfragenAppService, times(1)).getAlleUmfragen();
		assertTrue(model.containsAttribute("umfragen"));
		assertEquals("map/map", redirectStr);
	}
}
