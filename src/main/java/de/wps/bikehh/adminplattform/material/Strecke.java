package de.wps.bikehh.adminplattform.material;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity Strecke
 *
 * @author amnesica
 *
 */
@Entity
public class Strecke {

	@Id
	@GeneratedValue
	private int id;

	private double breitengrad;

	private double laengengrad;

	private Date erstelltAmDatum;

	@OneToMany(mappedBy = "track", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<UmfrageDB> listeAnUmfragen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public Date getErstelltAmDatum() {
		return erstelltAmDatum;
	}

	public void setErstelltAmDatum(Date erstelltAmDatum) {
		this.erstelltAmDatum = erstelltAmDatum;
	}

	public List<UmfrageDB> getListeAnUmfragen() {
		return listeAnUmfragen;
	}

	public void setListeAnUmfragen(List<UmfrageDB> listeAnUmfragen) {
		this.listeAnUmfragen = listeAnUmfragen;
	}

}
