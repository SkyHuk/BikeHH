package de.wps.bikehh.umfragenerstellen.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.umfragenerstellen.util.Utils;

public class UtilsTest {

	private Utils utils;

	public UtilsTest() {
		utils = new Utils();
	}

	@Test
	public void umfrageIstValideTest() {
		String jsonString = "{\"bestaetigtVonUsern\":[],\"ersteller\":{\"id\":1,\"emailAddress\":\"admin@chef.lol\",\"encryptedPassword\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"isVerified\":false,\"isLocked\":false,\"credibility\":0,\"privacySetting\":0,\"role\":\"administrator\",\"bestaetigteUmfragen\":[],\"updatedAt\":\"Jul 1, 2020 12:51:14 PM\",\"createdAt\":\"Jul 1, 2020 12:51:14 PM\"},\"erstelltAmDatum\":\"2020-07-01T10:53:21.825Z\",\"fahrtrichtung\":0,\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"wwwwwwwwwwwww\",\"startDatum\":\"2020-07-02\",\"endDatum\":\"2020-07-16\",\"bestaetigtSchwellenwert\":10,\"fragen\":[{\"titel\":\"eeeeeeeeeeeeeeeeeee\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"}],\"bedingungen\":[],\"fahrtrichtung\":0,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.543232197085494,\"breitengrad\":9.81731414794922,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		assertTrue(Utils.umfrageIstValide(umfrage));
	}
}
