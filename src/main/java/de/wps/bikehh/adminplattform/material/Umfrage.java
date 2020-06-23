package de.wps.bikehh.adminplattform.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.wps.bikehh.benutzerverwaltung.material.Benutzer;

/**
 * Test-Klasse für eine Umfrage (mit JSON-Dateien, non-Db-Version)
 *
 * @author amnesica
 *
 */

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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
	private String kategorie;
	private String startDatum;
	private String endDatum;
	private String erstelltAmDatum;
	private boolean istBestaetigt;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Benutzer> bestaetigtVonBenutzern;
	private int bestaetigtSchwellenwert;
	private String titel;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Frage> fragen;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Benutzer.class, cascade = CascadeType.ALL)
	private Benutzer ersteller;

	private boolean manuellErstellt;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Adresse.class, cascade = CascadeType.ALL)
	private Adresse adresse;

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

	public boolean isIstBestaetigt() {
		return istBestaetigt || getBestaetigtVonBenutzern().size() <= getBestaetigtSchwellenwert();
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

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public boolean isManuellErstellt() {
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

	public boolean wurdeManuellErstellt() {
		return manuellErstellt;
	}

	/*
	 * public Kategorie getKategorie() { return kategorie; }
	 * 
	 * public void setKategorie(Kategorie kategorie) { this.kategorie = kategorie; }
	 */
	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public List<Benutzer> getBestaetigtVonBenutzern() {
		return bestaetigtVonBenutzern;
	}

	public void setBestaetigtVonBenutzern(List<Benutzer> bestaetigtVonBenutzern) {
		this.bestaetigtVonBenutzern = bestaetigtVonBenutzern;
	}

	public List<Frage> getFragen() {
		return fragen;
	}

	public void setFragen(List<Frage> fragen) {
		this.fragen = fragen;
	}

	public Benutzer getErsteller() {
		return ersteller;
	}

	public void setErsteller(Benutzer ersteller) {
		this.ersteller = ersteller;
	}

}
