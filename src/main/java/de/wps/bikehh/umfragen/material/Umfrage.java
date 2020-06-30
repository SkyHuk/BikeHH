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
 * Test-Klasse für eine Umfrage (mit JSON-Dateien, non-Db-Version)
 *
 * @author amnesica
 *
 */

@Entity
public class Umfrage {

	@Id
	@GeneratedValue
	private int id;
	private double laengengrad;
	private double breitengrad;

	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	// @JoinColumn(name = "kategorie", referencedColumnName = "Id", nullable =
	// false)
	// private Kategorie kategorie;
	@ManyToOne(targetEntity = Kategorie.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Kategorie kategorie;
	private String startDatum;
	private String endDatum;
	private String erstelltAmDatum;
	private boolean istBestaetigt;
	private boolean bearbeitet;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> bestaetigtVonUsern;
	private int bestaetigtSchwellenwert;
	private String titel;

	@OneToMany(targetEntity = Frage.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "umfrage", referencedColumnName = "id", nullable = false)
	private List<Frage> fragen;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	private User ersteller;

	private boolean manuellErstellt;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Adresse.class, cascade = CascadeType.ALL)
	private Adresse adresse;

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

	@Override
	public String toString() {

		return "lat: " + getBreitengrad() + "lng: " + getLaengengrad() + "ersteller: "
				+ getErsteller().getEmailAddress();
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

}
