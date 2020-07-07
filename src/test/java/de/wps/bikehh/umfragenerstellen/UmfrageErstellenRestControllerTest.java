package de.wps.bikehh.umfragenerstellen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 3, 2020 12:29:31 PM\",\"createdAt\":\"Jul 3, 2020 12:29:31 PM\"},\"erstelltAmDatum\":\"2020-07-03T10:30:28.237Z\",\"fahrtrichtung\":-1.67149040556909,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Titel\",\"startDatum\":\"2020-07-03\",\"endDatum\":\"2020-07-04\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"Frage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.5428567609544,\"breitengrad\":9.851646423339846}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53976266845634,\"breitengrad\":9.831390380859377,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Heinz-Henk-Straße\"}}";
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);
		Calendar cal = Calendar.getInstance();
		String validDate = new SimpleDateFormat("YYYY-MM-dd").format(cal.getTime());
		System.out.println(validDate);
		umfrage1.setStartDatum(validDate);
		umfrage1.setEndDatum(validDate);
		jsonString = new Gson().toJson(umfrage1);

		umfragenErstellenRestController.speichereUmfrage(jsonString);

		// TODO implement below methods
		// Mockito.verify(umfragenRepository, times(1)).save(umfrage1);
		// Mockito.verify(umfragenRepository, times(1)).findById(umfrage1.getId());

		assertEquals(0, umfragenErstellenRestController.speichereUmfrage(jsonString));
	}
}