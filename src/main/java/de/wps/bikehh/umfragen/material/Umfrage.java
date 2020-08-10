package de.wps.bikehh.umfragen.material;

import java.time.LocalDate;
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
import de.wps.bikehh.umfragen.fachwert.Kategorie;

/**
 * Eine Umfrage stellt eine positionsbedingte Ansammlung von Fragen an einen
 * Benutzer dar.
 * 
 * Eine Umfrage gilt als bestätigt, wenn sie die erforderlichen Bestätigungen
 * erreicht hat, dargestellt durch einen Schwellenwert, oder wenn sie durch
 * einen Admin als bestaetigt markiert wurde.
 */
@Entity
public class Umfrage {

	@Id
	@GeneratedValue
	private long id;

	/**
	 * Titel der Umfrage
	 */
	private String titel;

	/**
	 * longitude in EPSG:3857
	 */
	private double laengengrad;

	/**
	 * latitude in EPSG:3857
	 */
	private double breitengrad;

	/**
	 * Kategorie der Umfrage
	 */
	@ManyToOne(targetEntity = Kategorie.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Kategorie kategorie;

	/**
	 * Startdatum des Zeitraumes, in welchem die Umfrage aktiv sein soll.
	 */
	private LocalDate startDatum;

	/**
	 * Enddatum des Zeitraumes, in welchem die Umfrage aktiv sein soll.
	 */
	private LocalDate endDatum;

	/**
	 * Erstellungsdatum der Umfrage
	 */
	private LocalDate createdAt;

	private LocalDate updatedAt;

	/**
	 * Ob die Umfrage als bestätigt gilt.
	 */
	private boolean istBestaetigt;

	/**
	 * Winkel im Bogenmaß nach Osten (Wertebereich 0 - 2pi, nach Osten weil das
	 * mathematischer Standard ist) eine fahrtrichtung von 0.0 ist ungültig und
	 * steht symbolisierend für "keine Fahrtrichtung gesetzt"
	 */
	private double fahrtrichtung;

	/**
	 * Liste an Usern, die diese Umfrage bestätigt haben
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> bestaetigtVonUsern;

	/**
	 * Wie viele User diese Umfrage bestätigen müsssen damit sie als bestätigt
	 * gilt
	 */
	private int bestaetigtSchwellenwert;

	/**
	 * Die Ansammlung von Fragen für diese Umfrage.
	 */
	@OneToMany(targetEntity = Frage.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "umfrage", referencedColumnName = "id", nullable = false)
	private List<Frage> fragen;

	/**
	 * Der Ersteller der Umfrage. Wenn die Umfrage automatisch generiert wurde
	 * (aus einer Meldung), dann sollte dies der Benutzer bzw. Radfahrer sein,
	 * der die Meldung abgesetzt hat.
	 */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	private User ersteller;

	/**
	 * Ob die Umfrage manuell durch einen Admin oder automatisch aus einer
	 * Meldung erstellt / generiert wurde.
	 */
	private boolean manuellErstellt;

	/**
	 * Der Ort der Umfrage, bezieht sich nur auf den Hauptmarker (blau), nicht
	 * auf die optionalen Orte der einzelnen Fragen.
	 */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Adresse.class, cascade = CascadeType.ALL)
	private Adresse adresse;

	/**
	 * Ob die Umfrage deaktiviert ist oder nicht.
	 */
	private boolean isDisabled;

	/**
	 * Explizite Merge-Funktion, um eine existierende Umfrage nach dem
	 * bearbeiten zu aktualisieren.
	 *
	 * @param umfrage
	 *            die neue Umfrage
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
		setCreatedAt(umfrage.getCreatedAt());
		setUpdatedAt(umfrage.getUpdatedAt());
		setFragen(umfrage.getFragen());
		setId(umfrage.getId());
		setIstBestaetigt(umfrage.getIstBestaetigt());
		setKategorie(umfrage.getKategorie());
		setManuellErstellt(umfrage.getManuellErstellt());
		setTitel(umfrage.getTitel());
		return umfrage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(LocalDate startDatum) {
		this.startDatum = startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(LocalDate endDatum) {
		this.endDatum = endDatum;
	}

	public boolean getIstBestaetigt() {
		istBestaetigt = istBestaetigt || getBestaetigtVonUsern().size() <= getBestaetigtSchwellenwert();
		return istBestaetigt;
	}

	public void setIstBestaetigt(boolean istBestaetigt) {
		this.istBestaetigt = istBestaetigt;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
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

	public boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
}
