package de.wps.bikehh.umfragenerstellen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.wps.bikehh.umfragen.material.Frage;
import de.wps.bikehh.umfragen.material.Umfrage;

/**
 *
 * Utilies Klasse, die Hilfsfunktionen im Umgang mit Umfragen anbietet
 *
 */
public class Validator {

	/**
	 * Validiert eine Umfrage
	 *
	 * @param umfrage Umfrage, die zu testen ist
	 * @return true, wenn Umfrage valide ist
	 */
	public static boolean umfrageIstValide(Umfrage umfrage) {
		System.out.println("Starte Validierung der Umfrage ...");
		if (umfrage != null) {
			Date startDatum = null;
			Date endDatum = null;

			try {
				// formatiere Daten um einen Vergleich zu ermoeglichen
				if (umfrage.getStartDatum() != null) {
					// moegliche ParseException
					startDatum = konvertiereStringDatumZuDate(umfrage.getStartDatum());
				}

				if (umfrage.getEndDatum() != null) {
					// moegliche ParseException
					endDatum = konvertiereStringDatumZuDate(umfrage.getEndDatum());
				}

				List<Frage> fragenOhneAntworten = new ArrayList<Frage>();
				for (Frage frage : umfrage.getFragen()) {
					if (!(frage.getFahrtrichtung() == 0.0 ||
						(frage.getFahrtrichtung() > 0 && frage.getFahrtrichtung() <= 2 * Math.PI)
					   )) {
						System.out.println("Fahrtrichtung einer Frage hat ein falsches Format");
						return false;
					}

					if (frage.getAntwortMoeglichkeiten().size() < 1) {
						fragenOhneAntworten.add(frage);
					}
				}
				boolean alleFragenOhneAntwortenErlaubenBenutzerDefinierteAntworten = true;
				for (Frage frage : fragenOhneAntworten) {
					if (!frage.isErlaubeBenutzerdefinierteAntwort()) {
						alleFragenOhneAntwortenErlaubenBenutzerDefinierteAntworten = false;
						break;
					}
				}

				// unten stehenden Print-Anweisungen helfen dabei, herauszufinden, was genau an
				// der Umfrage nicht stimmt

				/*
				 * System.out.println(umfrage.getTitel() != null);
				 * System.out.println(!umfrage.getTitel().isEmpty());
				 * System.out.println(startDatum != null); System.out.println(endDatum != null);
				 * System.out.println(startDatum.after(getHeutigesDatum()) ||
				 * startDatum.equals(getHeutigesDatum()) || umfrage.isBearbeitet());
				 * System.out.println(endDatum.after(startDatum) ||
				 * endDatum.equals(startDatum)); System.out.println(umfrage.getFragen() !=
				 * null); System.out.println(umfrage.getFragen().size() > 0 &&
				 * fragenHabenTitel(umfrage.getFragen()));
				 * System.out.println(fragenHabenAntworten(umfrage.getFragen()));
				 * System.out.println(alleFragenOhneAntwortenErlaubenBenutzerDefinierteAntworten
				 * ); System.out.println(umfrage.getErstelltAmDatum() != null &&
				 * !umfrage.getErstelltAmDatum().isEmpty());
				 * System.out.println(umfrage.getBreitengrad() != 0 && umfrage.getLaengengrad()
				 * != 0); System.out.println(umfrage.getKategorie() != null);
				 * System.out.println(umfrage.getBestaetigtVonUsern() != null);
				 * System.out.println(umfrage.getErsteller() != null && umfrage.getAdresse() !=
				 * null);
				 */

				// eigentliche Validierung
				if (umfrage.getTitel() != null && !umfrage.getTitel().isEmpty() && startDatum != null
						&& endDatum != null
						&& (startDatum.after(getHeutigesDatum()) || startDatum.equals(getHeutigesDatum())
								|| umfrage.isBearbeitet())
						&& (endDatum.after(startDatum) || endDatum.equals(startDatum)) && umfrage.getFragen() != null
						&& umfrage.getFragen().size() > 0 && fragenHabenTitel(umfrage.getFragen())
						&& (fragenHabenAntworten(umfrage.getFragen())
								|| alleFragenOhneAntwortenErlaubenBenutzerDefinierteAntworten)
						&& umfrage.getCreatedAt() != null && !umfrage.getCreatedAt().isEmpty()
						&& umfrage.getBreitengrad() != 0 && umfrage.getLaengengrad() != 0
						&& umfrage.getKategorie() != null && umfrage.getBestaetigtVonUsern() != null
						&& umfrage.getErsteller() != null && umfrage.getAdresse() != null
						&& (umfrage.getFahrtrichtung() == 0.0 ||
							(umfrage.getFahrtrichtung() > 0 && umfrage.getFahrtrichtung() <= 2 * Math.PI)
						   )
					) {

					System.out.println("Validierung war erfolgreich.");
					return true;
				} else {
					System.out.println(
							"Validierung war nicht erfolgreich. Ein oder mehrere Parameter sind nicht korrekt.");
					return false;
				}
			} catch (ParseException e) {
				System.out.println("Validierung war nicht erfolgreich. Eine ParseException trat auf.");
				return false;
			}
		}
		System.out.println("Validierung war nicht erfolgreich. Umfrage ist null");
		return false;

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
	private static boolean fragenHabenTitel(List<Frage> fragen) {
		boolean bFragenHabenTitel = false;
		for (Frage frage : fragen) {
			if (frage.getTitel() != null && !frage.getTitel().isEmpty()) {
				bFragenHabenTitel = true;
			} else {
				System.out.println("fragenHabenTitel gibt zurueck: " + false);
				return false;
			}
		}
		return bFragenHabenTitel;
	}

	/**
	 * Validiert das FragenArray und prüft, ob alle Fragen eine Antwort haben
	 *
	 * @param fragen
	 * @return true, if questions is valid
	 */
	private static boolean fragenHabenAntworten(List<Frage> fragen) {
		boolean bFragenHabenAntwort = false;
		for (Frage frage : fragen) {
			if (frage.getAntwortMoeglichkeiten() != null && frage.getAntwortMoeglichkeiten().size() > 0) {
				bFragenHabenAntwort = true;
			} else {
				System.out.println("fragenHabenAntworten gibt zurueck: " + false);
				return false;
			}
		}
		System.out.println("fragenHabenAntworten gibt zurueck: " + true);
		return bFragenHabenAntwort;
	}
}