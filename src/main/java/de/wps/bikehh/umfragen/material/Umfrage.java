package de.wps.bikehh.umfragen.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.wps.bikehh.benutzerverwaltung.material.User;

/**
 * Datenbank Entity
 *
 * Zentrale Umfrage-Klasse
 * 
 * eine Umfrage gilt als bestätigt, wenn sie die erforderlichen Bestätigungen
 * erreicht hat (bestaetigtSchwellenwert) oder wenn sie durch einen Admin als
 * bestaetigt markiert wurde, dies wird im istBestaetigt Feld gespeichert
 *
 */

@Entity
public class Umfrage {

	@Id
	@GeneratedValue
	private int id;

	// Titel der Umfrage
	private String titel;

	// koordinaten
	private double laengengrad;
	private double breitengrad;

	// Kategorie der Umfrage
	@ManyToOne(targetEntity = Kategorie.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Kategorie kategorie;

	// Zeitraum, in der die Umfrage aktiv sein soll
	private String startDatum;
	private String endDatum;

	// Erstellungsdatum
	private String erstelltAmDatum;

	/**
	 * ob die Umfrage als bestätigt gilt. Siehe Klassenkommentar und istBestaetigt()
	 * Methode.
	 * 
	 * TODO: Umfrage als bestaetigt markieren, wenn in umfrage.html auf den
	 * bestätigen Knopf gedrückt wird
	 */
	private boolean istBestaetigt;

	// ob die Umfrage schon mal bearbeitet wurde. Wichtig für Zeitraum und
	// Validierungsfunktion in Utils Klasse
	private boolean bearbeitet;

	// fahrtrichtung in Radiant, siehe Meldung.java
	private double fahrtrichtung;

	// Liste an Usern, die diese Umfrage bestätigt haben
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> bestaetigtVonUsern;

	// wie viele User diese Umfrage bestätigen müsssen, damit sie als bestätigt gilt
	private int bestaetigtSchwellenwert;

	@OneToMany(targetEntity = Frage.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "umfrage", referencedColumnName = "id", nullable = false)
	private List<Frage> fragen;

	/**
	 * der Ersteller der Umfrage. Wenn die Umfrage automatisch generiert wurde (aus
	 * einer Meldung), dann sollte dies der Benutzer (der Radfahrer) sein, der die
	 * Meldung abgesetzt hat. //TODO: Dies Implementieren
	 */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	private User ersteller;

	// ob die Umfrage manuell durch einen Admin oder automatisch aus einer Meldung
	// erstellt / generiert wurde
	private boolean manuellErstellt;

	/**
	 * der Ort der Umfrage, bezieht sich nur auf den Hauptmarker (blau), nicht auf
	 * die optionalen Orte der einzelnen Fragen
	 */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Adresse.class, cascade = CascadeType.ALL)
	private Adresse adresse;

	private boolean umfrageDisabled;

	/**
	 * explizite Merge-Funktion, um eine existierende Umfrage nach dem bearbeiten zu
	 * aktualiseren
	 * 
	 * @param umfrage die neue Umfrage
	 * @return die aktualisierte Umfrage
	 */
	public Umfrage merge(Umfrage umfrage) {
		setAdresse(umfrage.getAdresse());
		setBestaetigtSchwellenwert(umfrage.getBestaetigtSchwellenwert());
		setBestaetigtVonUsern(umfrage.getBestaetigtVonUsern());
		setBreitengrad(umfrage.getBreitengrad());
		setLaengengrad(umfrage.getLaengengrad());
		setEndDatum(umfrage.getEndDatum());
		setStartDatum(umfrage.getStartDatum());
		setErsteller(umfrage.getErsteller());
		setErstelltAmDatum(umfrage.getErstelltAmDatum());
		setFragen(umfrage.getFragen());
		setId(umfrage.getId());
		setIstBestaetigt(umfrage.istBestaetigt());
		setKategorie(umfrage.getKategorie());
		setManuellErstellt(umfrage.getManuellErstellt());
		setTitel(umfrage.getTitel());

		return umfrage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public boolean istBestaetigt() {
		return istBestaetigt || getBestaetigtVonUsern().size() <= getBestaetigtSchwellenwert();
	}

	public void setIstBestaetigt(boolean istBestaetigt) {
		this.istBestaetigt = istBestaetigt;
	}

	public String getErstelltAmDatum() {
		return erstelltAmDatum;
	}

	public void setErstelltAmDatum(String erstelltAmDatum) {
		this.erstelltAmDatum = erstelltAmDatum;
	}

	public int getBestaetigtSchwellenwert() {
		return bestaetigtSchwellenwert;
	}

	public void setBestaetigtSchwellenwert(int bestaetigtSchwellenwert) {
		this.bestaetigtSchwellenwert = bestaetigtSchwellenwert;
	}

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
	}

	public boolean isBearbeitet() {
		return bearbeitet;
	}

	public void setBearbeitet(boolean bearbeitet) {
		this.bearbeitet = bearbeitet;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public boolean getManuellErstellt() {
		return manuellErstellt;
	}

	public void setManuellErstellt(boolean manuellErstellt) {
		this.manuellErstellt = manuellErstellt;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public List<User> getBestaetigtVonUsern() {
		return bestaetigtVonUsern;
	}

	public void setBestaetigtVonUsern(List<User> bestaetigtVonUsern) {
		this.bestaetigtVonUsern = bestaetigtVonUsern;
	}

	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

	public User getErsteller() {
		return ersteller;
	}

	public void setErsteller(User ersteller) {
		this.ersteller = ersteller;
	}

	public boolean isUmfrageDisabled() {
		return umfrageDisabled;
	}

	public void setUmfrageDisabled(boolean umfrageDisabled) {
		this.umfrageDisabled = umfrageDisabled;
	}
}
