package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import de.wps.bikehh.umfragen.repository.UmfrageRepository;
import de.wps.bikehh.umfragen.service.UmfragenService;
import de.wps.bikehh.umfragenerstellen.controller.UmfrageErstellenRestController;

public class UmfrageErstellenRestControllerTest {

	private UmfrageRepository umfrageRepository;
	private UmfragenService umfragenService;
	private UmfrageErstellenRestController umfragenErstellenRestController;

	public UmfrageErstellenRestControllerTest() {
		// mocke Repository
		this.umfrageRepository = mock(UmfrageRepository.class);

		// mocke Service
		this.umfragenService = new UmfragenService(this.umfrageRepository);

		// mocke Controller
		this.umfragenErstellenRestController = new UmfrageErstellenRestController(this.umfragenService);
	}

	@Test
	public void vollstaendigeUmfragenWerdenRichtigValidiert() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		assertEquals(0, umfragenErstellenRestController.speichereUmfrage(jsonString));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageOhneTitleIstNichtValide() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenRestController.speichereUmfrage(jsonStringTitelFehlt);
		});

		String expectedMessage = "406 NOT_ACCEPTABLE \"Umfrage ist nicht valide und wird nicht gespeichert\"";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageStartdatumIstInVergangenheit() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-24\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenRestController.speichereUmfrage(jsonStringTitelFehlt);
		});

		String expectedMessage = "406 NOT_ACCEPTABLE \"Umfrage ist nicht valide und wird nicht gespeichert\"";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
		System.out.println("----Naechster Test----");
	}

	@Test
	public void umfrageEnddatumIstVorStartdatum() {
		String jsonStringTitelFehlt = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-24\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			umfragenErstellenRestController.speichereUmfrage(jsonStringTitelFehlt);
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
