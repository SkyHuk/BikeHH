package de.wps.bikehh.meldungen.material;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.benutzerverwaltung.material.User;

@Entity
public class Meldung {

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(mappedBy = "meldung", cascade = CascadeType.PERSIST)
	private Befragung befragung;

	@ManyToOne
	private User ersteller;

	private double laengengrad;
	private double breitengrad;

	private String text;

	private String kategorie;

	@CreationTimestamp
	private LocalDate createdAt;

	// TODO: Meldung soll Foto beinhalten können

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Befragung getBefragung() {
		return befragung;
	}

	public void setBefragung(Befragung befragung) {
		this.befragung = befragung;
	}

	public User getErsteller() {
		return ersteller;
	}

	public void setErsteller(User ersteller) {
		this.ersteller = ersteller;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

}
