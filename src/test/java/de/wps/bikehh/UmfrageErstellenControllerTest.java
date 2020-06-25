package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import de.wps.bikehh.benutzerverwaltung.material.Benutzer;
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
		Benutzer benutzer = new Benutzer(1l, "hashedPw", "email@email.de", "testUser", false, null);
		double[] koordinaten = { 50.0, 9.0 };

		assertEquals("adfc/umfrage_erstellen",
				this.umfragenErstellenController.zeigeUmfragenErsteller(benutzer, koordinaten, model));
	}

	@Test
	public void zeigeUmfragenBearbeiterTest() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		when(umfrageRepository.findById(umfrage.getId())).thenReturn(Optional.of(umfrage));

		assertEquals("adfc/umfrage_erstellen",
				this.umfragenErstellenController.zeigeUmfragenBearbeiter(umfrage.getId(), model));
	}
}
