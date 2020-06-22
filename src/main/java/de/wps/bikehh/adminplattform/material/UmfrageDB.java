package de.wps.bikehh.adminplattform.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import de.wps.bikehh.benutzerverwaltung.material.Benutzer;

/**
 * Entity Umfrage
 *
 * @author amnesica
 *
 */
@Entity
public class UmfrageDB {

	/*
	 * static Survey createFromJson(String jsonString) { JSON j =
	 * JSON.builder().register(JacksonAnnotationExtension.std).build(); }
	 */

	@Id
	@GeneratedValue
	private int id;

	private double laengengrad;

	private double breitengrad;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	@JoinColumn(name = "kategorie", referencedColumnName = "Id", nullable = false)
	private Kategorie kategorie;

	private Date erstelltAmDatum;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Benutzer> bestaetigtVonBenutzern = new ArrayList<Benutzer>();

	private boolean bestaetigt;

	private String text;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Strecke.class)
	@JoinColumn(name = "strecke", referencedColumnName = "Id", nullable = false)
	private Strecke strecke;

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

	public Kategorie getKategorie() {
		return kategorie;
	}

	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	public Date getErstelltAmDatum() {
		return erstelltAmDatum;
	}

	public void setErstelltAmDatum(Date erstelltAmDatum) {
		this.erstelltAmDatum = erstelltAmDatum;
	}

	public List<Benutzer> getBestaetigtVonBenutzern() {
		return bestaetigtVonBenutzern;
	}

	public void setBestaetigtVonBenutzern(List<Benutzer> bestaetigtVonBenutzern) {
		this.bestaetigtVonBenutzern = bestaetigtVonBenutzern;
	}

	public boolean isBestaetigt() {
		return bestaetigt;
	}

	public void setBestaetigt(boolean bestaetigt) {
		this.bestaetigt = bestaetigt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Strecke getStrecke() {
		return strecke;
	}

	public void setStrecke(Strecke strecke) {
		this.strecke = strecke;
	}

	public boolean istBestaetigt() {
		return bestaetigt;
	}
}
