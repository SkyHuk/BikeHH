package de.wps.bikehh.umfragenerstellen;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.controller.UmfrageErstellenRestController;

public class UmfrageErstellenRestControllerTest {

	private UmfrageRepository umfragenRepository;
	private UmfragenService umfragenService;
	private UmfrageErstellenRestController umfragenErstellenRestController;

	public UmfrageErstellenRestControllerTest() {
		// mocke Repository
		this.umfragenRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfragenRepository);

		// mocke Controller
		this.umfragenErstellenRestController = new UmfrageErstellenRestController(this.umfragenService);
	}

	@Test
	public void speichereUmfrageTest() {

	}

}
