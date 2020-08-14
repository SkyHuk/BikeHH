package de.wps.bikehh.befragungen.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.umfragen.material.Umfrage;

@Entity
public class Befragung {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * longitude in EPSG:3857
	 */
	private double laengengrad;

	/**
	 * latitude in EPSG:3857
	 */
	private double breitengrad;

	private double fahrtrichtung;

	/**
	 * Die Ansammlung von Fragen für diese Befragung.
	 */
	@OneToMany(mappedBy = "befragung", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Frage> fragen;

	@ManyToOne
	private User ersteller;

	@ManyToOne
	private Umfrage umfrage;

	@ManyToOne
	private Meldung meldung;

	private int bestaetigungsSchwellenwert;

	// TODO: anzahlBestaetigungen

	private boolean isDisabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		return this.getId().equals(that.getId()) && this.getLaengengrad() == that.getLaengengrad()
				&& this.getBreitengrad() == that.getBreitengrad();
	}

}
