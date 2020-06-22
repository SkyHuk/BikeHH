package de.wps.bikehh.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.wps.bikehh.adminplattform.material.Frage;
import de.wps.bikehh.adminplattform.material.Umfrage;

public class Utils {

	private static Path speicherVerzeichnis = Paths.get(System.getProperty("user.dir"), "src", "main", "resources",
			"Umfragen");

	/**
	 * Gibt die Umfragen (JSONs) aus dem Speicher als List<Umfrage> zurueck
	 * 
	 * @return List<Umfrage>
	 */
	public static List<Umfrage> getUmfragenAusSpeicher() {
		List<Umfrage> umfragen = new ArrayList<Umfrage>();

		// hole Dateien aus Ordner als JsonArray
		umfragen = getJsonFilesAsSurveyList(speicherVerzeichnis.toString());

		return umfragen;
	}

	public static Umfrage getUmfrageNachId(int Id) {
		List<Umfrage> umfragen = getUmfragenAusSpeicher();
		for (Umfrage umfrage : umfragen) {
			if (umfrage.getId() == Id) {
				return umfrage;
			}
		}
		return null;
	}

	/**
	 * Speichert einen JsonString in bestimmten Ordner ab
	 *
	 * @param JSONString
	 * @throws ParseException
	 */
	@SuppressWarnings("unused")
	public static void speichereJSONStringImSpeicher(String JSONString)
			throws IllegalArgumentException, ParseException {
		FileWriter fileWriter = null;

		// Validiere jsonString durch Umwandlung in Umfrage
		Gson g = new Gson();
		Umfrage umfrage = g.fromJson(JSONString, Umfrage.class);

		int dateinamenZaehler = umfrage.getId();
		String dateiname = "Umfrage" + String.valueOf(dateinamenZaehler) + ".json";

		if (umfrage != null) {

			Date startDatum = null;
			Date endDatum = null;

			// formatiere Daten um einen Vergleich zu ermoeglichen
			if (umfrage.getStartDatum() != null) {
				// moegliche ParseException
				startDatum = konvertiereStringDatumZuDate(umfrage.getStartDatum());
				// System.out.println("dateFrom successfully parsed: " + dateFrom);
			}
			if (umfrage.getEndDatum() != null) {
				// moegliche ParseException
				endDatum = konvertiereStringDatumZuDate(umfrage.getEndDatum());
				// System.out.println("dateTo successfully parsed: " + dateTo);
			}

//			// Wichtig zum debuggen
//			if ((dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()))) {
//				System.out.println("dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()) returns: "
//						+ (dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate())));
//			} else {
//				System.out.println("dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()) returns: "
//						+ (dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate())));
//			}
//
//			if ((dateTo.after(dateFrom) || dateTo.equals(dateFrom))) {
//				System.out.println("(dateTo.after(dateFrom) || dateTo.equals(dateFrom)) returns: "
//						+ (dateTo.after(dateFrom) || dateTo.equals(dateFrom)));
//			} else {
//				System.out.println("(dateTo.after(dateFrom) || dateTo.equals(dateFrom)) returns: "
//						+ (dateTo.after(dateFrom) || dateTo.equals(dateFrom)));
//			}

			System.out.println("Validierung wird gestartet...");

			// TODO teste mit Bedingungen
			// validate Survey and save to files
			if (umfrage.getTitel() != null && !umfrage.getTitel().isEmpty() && startDatum != null && endDatum != null
					&& (startDatum.after(getHeutigesDatum()) || startDatum.equals(getHeutigesDatum()))
					&& (endDatum.after(startDatum) || endDatum.equals(startDatum)) && umfrage.getFragen() != null
					&& umfrage.getFragen().length > 0 && fragenHabenTitel(umfrage.getFragen())
					&& fragenHabenAntworten(umfrage.getFragen()) && umfrage.getErstelltAmDatum() != null
					&& !umfrage.getErstelltAmDatum().isEmpty() && umfrage.getBreitengrad() != 0
					&& umfrage.getLaengengrad() != 0 && umfrage.getKategorie() != null
					&& !umfrage.getKategorie().isEmpty() && umfrage.getBestaetigtVonBenutzern() != null
					&& umfrage.getErsteller() != null && !umfrage.getErsteller().isEmpty()
					&& umfrage.getAdresse() != null) {

				System.out
						.println("JSONString ist valide. Beginne Speicherung des JSONs im angegebenen Verzeichnis ...");

				// speichere JSON
				try {
					// erstelle Datei
					File dateiZumSpeichern = new File(speicherVerzeichnis + "/" + dateiname);
					if (dateiZumSpeichern.createNewFile()) {
						System.out.println("Datei erstellt: " + dateiZumSpeichern.getName());
					} else {
						System.out.println("Datei existiert bereits und wird überschrieben.");
					}

					// schreibe in Datei
					fileWriter = new FileWriter(speicherVerzeichnis + "/" + dateiname);
					// konvertiere Umfrage in jsonString
					String jsonString = g.toJson(umfrage);

					fileWriter.write(jsonString);
					System.out.println("JSON-Objekt " + dateiname + " wurde erfolgreich in " + speicherVerzeichnis
							+ "gespeichert");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fileWriter.flush();
						fileWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("----- If-Bedingung schlug fehl -----");
//			// print survey
//			System.out.println("----- survey info -----");
//			System.out.println("id: " + surveyTest.getId());
//			System.out.println("lng: " + surveyTest.getLng());
//			System.out.println("lat: " + surveyTest.getLat());
//			System.out.println("category: " + surveyTest.getCategory());
//			System.out.println("dateFrom: " + surveyTest.getDateFrom());
//			System.out.println("dateTo: " + surveyTest.getDateTo());
//			System.out.println("createdAtDate: " + surveyTest.getCreatedAtDate());
//			System.out.println("confirmedByUsers: " + surveyTest.getConfirmedByUsers());
//			System.out.println("confirmedThreshhold: " + surveyTest.getConfirmedThreshhold());
//			System.out.println("title: " + surveyTest.getTitle());
//			System.out.println("questions: " + surveyTest.getQuestions());
//			System.out.println("creator: " + surveyTest.getCreator());
//			System.out.println("createdManually: " + surveyTest.isCreatedManually());
//			System.out.println("address: " + surveyTest.getAddress().toString());

			// throw Exception
			throw new IllegalArgumentException(
					"Umfrage " + umfrage + " hat nicht das richtige Format und wird daher nicht gespeichert!");
		}
	}

	/**
	 * Gibt JsonString im Dateipfad als Liste an Umfragen zurueck
	 * 
	 * @param speicherVerzeichnis
	 * @return List<SurveyTest>
	 */
	private static List<Umfrage> getJsonFilesAsSurveyList(String speicherVerzeichnis) {
		// JSONObjects in JSONArray um sie am Thymeleaf zu geben
		List<Umfrage> umfragenListe = new ArrayList<>();

		try {
			File datei = new File(speicherVerzeichnis);

			FilenameFilter dateiFilter = new FilenameFilter() {
				@Override
				public boolean accept(File datei, String name) {
					// wir wollen nur .json-Dateien finden
					return name.endsWith(".json");
				}
			};

			// Array mit allen Dateien die eine .json-Endung haben
			File[] dateien = datei.listFiles(dateiFilter);

			// Zeige Anzahl an gefundenen Dateien (nur zum debuggen)
			System.out.println("Anzahl gefundener Dateien: " + dateien.length);

			Gson g = new Gson();

			// füge jede JSON-Datei in dateien zum jsonArray hinzu
			for (File jsonDatei : dateien) {
				// parse Datei
				Object obj = JsonParser.parseReader(new FileReader(jsonDatei));
				// konvertiere zu JsonObjekt
				JsonObject JSONObjekt = (JsonObject) obj;
				// füge JSONObjekt zur Liste von Umfragen hinzu
				Umfrage umfrage = g.fromJson(JSONObjekt, Umfrage.class);
				umfragenListe.add(umfrage);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Liste an Umfragen konnte erfolgreich aus Dateien erstellt werden");
		return umfragenListe;
	}

	/**
	 * Gibt das heutige Datum mit yyyy-MM-dd als Date zurueck
	 * 
	 * @return Date
	 * @throws ParseException
	 */
	private static Date getHeutigesDatum() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// System.out.println("getCurrentDate returns: " +
		// convertStringDateToDate(formatter.format(date)));
		return konvertiereStringDatumZuDate(formatter.format(date));
	}

	/**
	 * Konvertiert einen String zu einem Datum (Date)
	 * 
	 * @param stringDatum String
	 * @return Date
	 * @throws ParseException
	 */
	private static Date konvertiereStringDatumZuDate(String stringDatum) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(stringDatum);
	}

	/**
	 * Validiert das FragenArray und prüft, ob alle Fragen einen Titel haben
	 * 
	 * @param fragen
	 * @return true, if questions is valid
	 */
	private static boolean fragenHabenTitel(Frage[] fragen) {
		boolean bFragenHabenTitel = false;
		for (Frage frage : fragen) {
			if (frage.getTitel() != null && !frage.getTitel().isEmpty()) {
				bFragenHabenTitel = true;
			} else {
				System.out.println("fragenHabenTitel gibt zurueck: " + false);
				return false;
			}
		}
		// System.out.println("questionsHaveTitle return: " + bQuestionsHaveTitle);
		return bFragenHabenTitel;
	}

	/**
	 * Valisiert das FragenArray und prüft, ob alle Fragen eine Antwort haben
	 * 
	 * @param fragen
	 * @return true, if questions is valid
	 */
	private static boolean fragenHabenAntworten(Frage[] fragen) {
		boolean bFragenHabenAntwort = false;
		for (Frage frage : fragen) {
			if (frage.getAntworten() != null && frage.getAntworten().size() > 0) {
				bFragenHabenAntwort = true;
			} else {
				System.out.println("fragenHabenAntworten gibt zurueck: " + false);
				return false;
			}
		}
		// System.out.println("questionsHaveAnswer return: " + bQuestionsHaveAnswer);
		return bFragenHabenAntwort;
	}
}

/*
 * // OLD METHOD, replaced by saveJSONSurveyInFiles() public static void
 * saveJSONSurveyInFilesOld(String JSONString) { FileWriter file = null; int
 * filenameCounter = (int) (Math.random() * 999999 + 1); String filename =
 * "Umfrage" + String.valueOf(filenameCounter) + ".json";
 * 
 * // get SurveyTest from jsonString to validate jsonString Gson g = new Gson();
 * SurveyTest surveyTest = g.fromJson(JSONString, SurveyTest.class);
 * 
 * // save file try { // create file File fileToSaveIn = new File(startDir + "/"
 * + filename); if (fileToSaveIn.createNewFile()) {
 * System.out.println("File created: " + fileToSaveIn.getName()); } else {
 * System.out.println("File already exists."); }
 * 
 * // write to file file = new FileWriter(startDir + "/" + filename); // covnert
 * SurveyTest to jsonString String jsonString = g.toJson(surveyTest); // old:
 * file.write(JSONString); file.write(jsonString);
 * System.out.println("Successfully Copied JSON Object " + filename + " to " +
 * startDir); } catch (IOException e) { e.printStackTrace(); } finally { try {
 * file.flush(); file.close(); } catch (IOException e) { e.printStackTrace(); }
 * } }
 */
