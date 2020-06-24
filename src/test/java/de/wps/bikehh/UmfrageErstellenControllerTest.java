package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.controller.UmfrageErstellenController;

public class UmfrageErstellenControllerTest {

	private Umfrage umfrage;
	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private UmfrageErstellenController umfragenErstellenController;

	public UmfrageErstellenControllerTest() {
		this.umfrage = mock(Umfrage.class);

		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);
		when(this.umfrageRepository.save(umfrage)).thenReturn(umfrage);

		// mocke Service
		this.umfragenService = new UmfragenService();
		this.umfragenService.setUmfrageRepository(this.umfrageRepository);

		// mocke Controller
		this.umfragenErstellenController = new UmfrageErstellenController();
		this.umfragenErstellenController.setUmfragenService(this.umfragenService);
	}

	@Test
	public void vollstaendigeUmfragenWerdenRichtigValidiert() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-24T13:04:07.908Z\",\"manuellErstellt\":true,\"titel\":\"TitelDerUmfrage\",\"startDatum\":\"2020-06-24\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"TitelFrage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true,\"laengengrad\":53.541323635096255,\"breitengrad\":9.843106269836428},{\"titel\":\"TitelFrage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[{\"frage\":\"TitelFrage1\",\"erwarteteAntwort\":\"Ja\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.538673691728555,\"breitengrad\":9.845724105834963}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53707140353033,\"breitengrad\":9.83908653171966,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Neßdeich\"}}";
		assertEquals(0, umfragenErstellenController.speichereUmfrage(jsonString));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageOhneTitleIstNichtValide() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-24T13:04:07.908Z\",\"manuellErstellt\":true,\"titel\":\"\",\"startDatum\":\"2020-06-24\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"TitelFrage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true,\"laengengrad\":53.541323635096255,\"breitengrad\":9.843106269836428},{\"titel\":\"TitelFrage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[{\"frage\":\"TitelFrage1\",\"erwarteteAntwort\":\"Ja\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.538673691728555,\"breitengrad\":9.845724105834963}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53707140353033,\"breitengrad\":9.83908653171966,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Neßdeich\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenController.speichereUmfrage(jsonStringTitelFehlt);
		});

		String expectedMessage = "406 NOT_ACCEPTABLE \"Umfrage ist nicht valide und wird nicht gespeichert\"";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageStartdatumIstInVergangenheit() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-24T13:04:07.908Z\",\"manuellErstellt\":true,\"titel\":\"\",\"startDatum\":\"2020-06-20\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"TitelFrage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true,\"laengengrad\":53.541323635096255,\"breitengrad\":9.843106269836428},{\"titel\":\"TitelFrage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[{\"frage\":\"TitelFrage1\",\"erwarteteAntwort\":\"Ja\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.538673691728555,\"breitengrad\":9.845724105834963}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53707140353033,\"breitengrad\":9.83908653171966,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Neßdeich\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenController.speichereUmfrage(jsonStringTitelFehlt);
		});

		String expectedMessage = "406 NOT_ACCEPTABLE \"Umfrage ist nicht valide und wird nicht gespeichert\"";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageEnddatumIstVorStartdatum() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-24T13:04:07.908Z\",\"manuellErstellt\":true,\"titel\":\"\",\"startDatum\":\"2020-06-24\",\"endDatum\":\"2020-06-23\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"TitelFrage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true,\"laengengrad\":53.541323635096255,\"breitengrad\":9.843106269836428},{\"titel\":\"TitelFrage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[{\"frage\":\"TitelFrage1\",\"erwarteteAntwort\":\"Ja\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.538673691728555,\"breitengrad\":9.845724105834963}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53707140353033,\"breitengrad\":9.83908653171966,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Neßdeich\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenController.speichereUmfrage(jsonStringTitelFehlt);
		});

		String expectedMessage = "406 NOT_ACCEPTABLE \"Umfrage ist nicht valide und wird nicht gespeichert\"";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("----Naechster Test----");
	}

	// TODO weitere Tests
}

//old
/*
 * Benutzer benutzer = new Benutzer(1l, "hashedPw", "benutzer@email.de", "user",
 * false, new ArrayList<Umfrage>()); int id; double laengengrad = 53.536035;
 * double breitengrad = 9.837099; Kategorie kategorie = new Kategorie(1,
 * "TestKategorie"); String startDatum = new
 * SimpleDateFormat("yyyy-MM-dd").format(new Date()); String endDatum =
 * LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new
 * Date())).plusDays(1).toString(); String erstelltAmDatum = new
 * SimpleDateFormat("yyyy-MM-dd").format(new Date()) boolean istBestaetigt =
 * false; List<Benutzer> bestaetigtVonBenutzern = new ArrayList<Benutzer>(); int
 * bestaetigtSchwellenwert = 10; String titel = "TestTitel";
 * 
 * List<Frage> fragen = new ArrayList<Frage>(); //fragen.add(new Frage())
 * 
 * Benutzer ersteller; boolean manuellErstellt; Adresse adresse;
 * 
 * umfrage = new Umfrage(id, laengengrad, breitengrad,
 * kategorie,startDatum,endDatum, erstelltAmDatum, istBestaetigt,
 * bestaetigtVonBenutzern, bestaetigtSchwellenwert, titel, fragen, ersteller,
 * manuellErstellt, adresse); // umfrage = new Gson().fromJson(jsonString,
 * Umfrage.class);
 */
