package de.wps.bikehh.umfragenerstellen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.controller.UmfrageErstellenController;

public class UmfrageErstellenControllerTest {

	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private UmfrageErstellenController umfragenErstellenController;
	private Model model;

	public UmfrageErstellenControllerTest() {
		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfrageRepository);

		// mocke Controller
		this.umfragenErstellenController = new UmfrageErstellenController(this.umfragenService);

		// mocke Model
		model = mock(Model.class);
	}

	@Test
	public void zeigeUmfragenErstellerTest() {

		User user = new User("email@email.de", "hashedPw", "testUser");
		double[] koordinaten = { 50.0, 9.0 };

		assertEquals("umfrageerstellen/umfrage_erstellen",
				umfragenErstellenController.zeigeUmfragenErsteller(user, koordinaten, model));
	}

	@Test
	public void zeigeUmfragenBearbeiterTest() {
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 8, 2020, 8:28:03 PM\",\"createdAt\":\"Jul 8, 2020, 8:28:03 PM\"},\"erstelltAmDatum\":\"2020-07-08T18:30:38.652Z\",\"umfrageDisabled\":false,\"fahrtrichtung\":-2.1142974543149453,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Test\",\"startDatum\":\"2020-07-08\",\"endDatum\":\"2020-07-31\",\"bestaetigtSchwellenwert\":10,\"fragen\":[{\"titel\":\"Frage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Frage1\",\"erwarteteAntwort\":\"Vielleicht\"}],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":true}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.56261550524817,\"breitengrad\":10.00133514404297,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"20148\",\"strasse\":\"Alsterufer\"}}";
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);

		when(umfrageRepository.findById(umfrage1.getId())).thenReturn(Optional.of(umfrage1));

		umfragenErstellenController.zeigeUmfragenBearbeiter(umfrage1.getId(), model);
		Mockito.verify(umfrageRepository, times(1)).findById(umfrage1.getId());

		assertEquals("umfrageerstellen/umfrage_erstellen",
				umfragenErstellenController.zeigeUmfragenBearbeiter(umfrage1.getId(), model));
	}
}
