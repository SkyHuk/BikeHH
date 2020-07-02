package de.wps.bikehh.umfragenerstellen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;
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
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 1, 2020 12:51:14 PM\",\"createdAt\":\"Jul 1, 2020 12:51:14 PM\"},\"erstelltAmDatum\":\"2020-07-01T10:53:21.825Z\",\"fahrtrichtung\":0,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"wwwwwwwwwwwww\",\"startDatum\":\"2020-07-02\",\"endDatum\":\"2020-07-16\",\"bestaetigtSchwellenwert\":10,\"fragen\":[{\"titel\":\"eeeeeeeeeeeeeeeeeee\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.543232197085494,\"breitengrad\":9.81731414794922,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);

		umfragenErstellenRestController.speichereUmfrage(jsonString);
		// TODO help
		// Mockito.verify(umfragenRepository, times(1)).save(umfrage1);
		// Mockito.verify(umfragenRepository, times(1)).findById(umfrage1.getId());

		assertEquals(0, umfragenErstellenRestController.speichereUmfrage(jsonString));
	}
}