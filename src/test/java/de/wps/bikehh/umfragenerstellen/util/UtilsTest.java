package de.wps.bikehh.umfragenerstellen.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;

public class UtilsTest {

	private Utils utils;

	public UtilsTest() {
		utils = new Utils();
	}

	@Test
	public void umfrageIstValideTest() {
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 3, 2020 12:29:31 PM\",\"createdAt\":\"Jul 3, 2020 12:29:31 PM\"},\"erstelltAmDatum\":\"2020-07-03T10:30:28.237Z\",\"fahrtrichtung\":-1.67149040556909,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Titel\",\"startDatum\":\"2020-07-03\",\"endDatum\":\"2020-07-04\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"Frage1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.5428567609544,\"breitengrad\":9.851646423339846}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53976266845634,\"breitengrad\":9.831390380859377,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Heinz-Henk-Straße\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		assertTrue(Utils.umfrageIstValide(umfrage));
	}
}
