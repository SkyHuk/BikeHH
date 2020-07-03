package de.wps.bikehh.umfragen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
	private String jsonString;

	public UmfragenControllerTest() {
		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfrageRepository);

		// mocke Controller
		this.umfragenController = new UmfragenController(this.umfragenService);

		// mock Model
		model = mock(Model.class);

		// test jsonString
		jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 3, 2020 12:29:31 PM\",\"createdAt\":\"Jul 3, 2020 12:29:31 PM\"},\"erstelltAmDatum\":\"2020-07-03T10:30:28.237Z\",\"fahrtrichtung\":-1.67149040556909,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Titel\",\"startDatum\":\"2020-07-03\",\"endDatum\":\"2020-07-04\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"Frage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.5428567609544,\"breitengrad\":9.851646423339846}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53976266845634,\"breitengrad\":9.831390380859377,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Heinz-Henk-Straße\"}}";
	}

	@Test
	public void zeigeUmfragenListeTest() {
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);
		Umfrage umfrage2 = new Gson().fromJson(jsonString, Umfrage.class);

		List<Umfrage> alleUmfragen = new ArrayList<Umfrage>();
		alleUmfragen.add(umfrage1);
		alleUmfragen.add(umfrage2);

		when(umfrageRepository.findAll()).thenReturn(alleUmfragen);

		umfragenController.zeigeUmfragenListe(model);
		Mockito.verify(umfrageRepository, times(1)).findAll();

		assertEquals("adfc/umfragen_liste", umfragenController.zeigeUmfragenListe(model));
	}

	@Test
	public void zeigeEinzelUmfrageTest() {
		Umfrage umfrage1 = new Gson().fromJson(jsonString, Umfrage.class);

		when(umfrageRepository.findById(umfrage1.getId())).thenReturn(Optional.of(umfrage1));
		umfrageRepository.save(umfrage1);
		Mockito.verify(umfrageRepository, times(1)).save(umfrage1);

		umfragenController.zeigeEinzelUmfrage(model, umfrage1.getId());

		when(umfrageRepository.findById(umfrage1.getId())).thenReturn(Optional.of(umfrage1));
		Mockito.verify(umfrageRepository, times(1)).findById(umfrage1.getId());

		assertEquals("adfc/umfrage", umfragenController.zeigeEinzelUmfrage(model, umfrage1.getId()));
	}

}
