package de.wps.bikehh.umfragenerstellen.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;

public class UtilsTest {

	@Test
	public void umfrageIstValideTest() {
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 8, 2020, 8:28:03 PM\",\"createdAt\":\"Jul 8, 2020, 8:28:03 PM\"},\"erstelltAmDatum\":\"2020-07-08T18:30:38.652Z\",\"umfrageDisabled\":false,\"fahrtrichtung\":-2.1142974543149453,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Test\",\"startDatum\":\"2020-07-08\",\"endDatum\":\"2020-07-31\",\"bestaetigtSchwellenwert\":10,\"fragen\":[{\"titel\":\"Frage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Frage1\",\"erwarteteAntwort\":\"Vielleicht\"}],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":true}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.56261550524817,\"breitengrad\":10.00133514404297,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"20148\",\"strasse\":\"Alsterufer\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);
		Calendar cal = Calendar.getInstance();
		String validDate = new SimpleDateFormat("YYYY-MM-dd").format(cal.getTime());
		System.out.println(validDate);
		umfrage.setStartDatum(validDate);
		umfrage.setEndDatum(validDate);
		assertTrue(Utils.umfrageIstValide(umfrage));
	}
}
