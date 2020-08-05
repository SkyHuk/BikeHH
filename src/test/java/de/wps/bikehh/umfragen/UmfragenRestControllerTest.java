package de.wps.bikehh.umfragen;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.wps.bikehh.umfragen.api.applicationservice.UmfragenRestApplicationService;
import de.wps.bikehh.umfragen.api.controller.UmfragenRestController;
import de.wps.bikehh.umfragen.api.dto.UmfrageAntwortRestDto;

public class UmfragenRestControllerTest {

	private UmfragenRestApplicationService umfragenRestAppService;
	private UmfragenRestController umfragenRestController;
	private UmfrageAntwortRestDto testUmfrage;

	@Before
	public void init() {
		umfragenRestAppService = mock(UmfragenRestApplicationService.class);
		umfragenRestController = new UmfragenRestController(umfragenRestAppService);

		testUmfrage = new UmfrageAntwortRestDto();
		testUmfrage.setUmfrageId(1);

		when(umfragenRestAppService.hasUmfrage(testUmfrage.getUmfrageId())).thenReturn(true);
	}

	@Test
	public void testpostUmfrageAntwort_beantwortetUmfrage() {
		// act
		ResponseEntity<String> response = umfragenRestController.postUmfrageAntwort(testUmfrage);

		// assert
		verify(umfragenRestAppService, times(1)).beantworteUmfrage(testUmfrage);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
