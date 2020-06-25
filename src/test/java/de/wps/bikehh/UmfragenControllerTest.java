package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.controller.UmfragenController;
import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;

public class UmfragenControllerTest {

	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private UmfragenController umfragenController;
	private Model model;

	public UmfragenControllerTest() {
		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfrageRepository);

		// mocke Controller
		this.umfragenController = new UmfragenController(this.umfragenService);

		// mock Model
		model = mock(Model.class);
	}

	@Test
	public void zeigeUmfragenListeTest() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);
		Umfrage umfrage2 = new Gson().fromJson(jsonString, Umfrage.class);

		List<Umfrage> alleUmfragen = new ArrayList<Umfrage>();
		alleUmfragen.add(umfrage1);
		alleUmfragen.add(umfrage2);

		when(umfrageRepository.findAll()).thenReturn(alleUmfragen);
		assertEquals("adfc/umfragen_liste", umfragenController.zeigeUmfragenListe(model));
	}

	@Test
	public void zeigeEinzelUmfrageTest() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);

		when(umfrageRepository.findById(umfrage1.getId())).thenReturn(Optional.of(umfrage1));

		assertEquals("adfc/umfrage", umfragenController.zeigeEinzelUmfrage(model, umfrage1.getId()));
	}

}
