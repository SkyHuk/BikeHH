package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import de.wps.bikehh.umfragen.material.Umfrage;
import de.wps.bikehh.utilities.Utils;

public class UtilsTest {

	private Utils utils;

	public UtilsTest() {
		utils = new Utils();
	}

	@Test
	public void umfrageIstValideTest() {
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-25T06:15:49.044Z\",\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-25\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"11\",\"fragen\":[{\"titel\":\"Titel der ersten  Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":true},{\"titel\":\"Titel der zweiten Frage\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[{\"frage\":\"Titel der ersten  Frage\",\"erwarteteAntwort\":\"Vielleicht\"}],\"pfeil\":null,\"erlaubeBenutzerdefinierteAntwort\":false}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.53950793001356,\"breitengrad\":9.840488433837892,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Franz-Josef-Strauß Straße\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		assertTrue(Utils.umfrageIstValide(umfrage));
	}
}
