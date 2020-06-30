package de.wps.bikehh;

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
		String jsonString = "{\"bestaetigtVonBenutzern\":[],\"ersteller\":{\"id\":1,\"verschluesseltesPasswort\":\"sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=\",\"emailAdresse\":\"admin@chef.lol\",\"rolle\":\"administrator\",\"istGesperrt\":false,\"bestaetigteUmfragen\":[]},\"erstelltAmDatum\":\"2020-06-26T09:58:44.816Z\",\"bearbeitet\":false,\"manuellErstellt\":true,\"titel\":\"Titel der Umfrage\",\"startDatum\":\"2020-06-26\",\"endDatum\":\"2020-06-30\",\"bestaetigtSchwellenwert\":\"15\",\"fragen\":[{\"titel\":\"Frage 1\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"},{\"text\":\"Nein\"},{\"text\":\"Vielleicht\"}],\"bedingungen\":[],\"fahrtrichtung\":false,\"pfeillinie\":null,\"pfeilspitze\":null,\"drehMarker\":null,\"erlaubeBenutzerdefinierteAntwort\":true,\"laengengrad\":53.53614836458142,\"breitengrad\":9.837441444396974},{\"titel\":\"Frage2\",\"antwortMoeglichkeiten\":[{\"text\":\"Ja\"}],\"bedingungen\":[],\"fahrtrichtung\":false,\"pfeillinie\":null,\"pfeilspitze\":null,\"drehMarker\":null,\"erlaubeBenutzerdefinierteAntwort\":false,\"laengengrad\":53.53324531654494,\"breitengrad\":9.83106851577759}],\"kategorie\":{\"name\":\"Verkehrsführung\"},\"laengengrad\":53.534518258368294,\"breitengrad\":9.83379364013672,\"adresse\":{\"stadt\":\"Hamburg\",\"postleitZahl\":\"21129\",\"strasse\":\"Neßdeich\"}}";
		Umfrage umfrage = new Gson().fromJson(jsonString, Umfrage.class);

		assertTrue(Utils.umfrageIstValide(umfrage));
	}
}
