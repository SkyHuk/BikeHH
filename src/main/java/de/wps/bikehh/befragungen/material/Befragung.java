package de.wps.bikehh.befragungen.material;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.umfragen.material.Umfrage;

@Entity
public class Befragung {

	@Id
	@GeneratedValue
	private long id;

	/**
	 * longitude in EPSG:4326
	 */
	private double laengengrad;

	/**
	 * latitude in EPSG:4326
	 */
	private double breitengrad;

	private double fahrtrichtung;

	private LocalDate startDatum;
	private LocalDate endDatum;

	/**
	 * Die Ansammlung von Fragen für diese Befragung.
	 */
	@OneToMany(mappedBy = "befragung", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Frage> fragen;

	@ManyToOne
	private User ersteller;

	@ManyToOne
	private Umfrage umfrage;

	@OneToOne
	private Meldung meldung;

	private int bestaetigungsSchwellenwert;

	// TODO: anzahlBestaetigungen

	private boolean isDisabled;

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

	public double getFahrtrichtung() {
		return fahrtrichtung;
	}

	public void setFahrtrichtung(double fahrtrichtung) {
		this.fahrtrichtung = fahrtrichtung;
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

	public Umfrage getUmfrage() {
		return umfrage;
	}

	public void setUmfrage(Umfrage umfrage) {
		this.umfrage = umfrage;
	}

	public Meldung getMeldung() {
		return meldung;
	}

	public void setMeldung(Meldung meldung) {
		this.meldung = meldung;
	}

	public int getBestaetigungsSchwellenwert() {
		return bestaetigungsSchwellenwert;
	}

	public void setBestaetigungsSchwellenwert(int bestaetigungsSchwellenwert) {
		this.bestaetigungsSchwellenwert = bestaetigungsSchwellenwert;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!o.getClass().equals(Befragung.class)) {
			return false;
		}
		Befragung that = (Befragung) o;
		return this.getId() == that.getId() && this.getLaengengrad() == that.getLaengengrad()
				&& this.getBreitengrad() == that.getBreitengrad();
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

	public boolean istAusUmfrage() {
		return umfrage != null;
	}

	public boolean istAusMeldung() {
		return meldung != null;
	}

}
