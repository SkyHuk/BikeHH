package de.wps.bikehh.karte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import de.wps.bikehh.karte.controller.KartenController;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class KartenControllerTest {

	private UmfrageRepository umfragenRepository;
	private UmfragenService umfragenService;
	private KartenController kartenController;
	private Model model;

	public KartenControllerTest() {
		// mocke Repository
		this.umfragenRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfragenRepository);

		// KartenController
		this.kartenController = new KartenController(umfragenService);

		// mock Model
		model = mock(Model.class);
	}

	@Test
	public void zeigeKarteTest() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);
		List<Umfrage> umfragen = new ArrayList<Umfrage>();
		umfragen.add(umfrage);

		when(umfragenRepository.findAll()).thenReturn(umfragen);

		kartenController.zeigeKarte(model);
		Mockito.verify(umfragenRepository, times(1)).findAll();

		assertEquals("adfc/karte", this.kartenController.zeigeKarte(model));
	}
}
