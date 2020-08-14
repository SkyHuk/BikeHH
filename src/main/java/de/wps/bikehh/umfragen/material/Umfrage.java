package de.wps.bikehh.umfragen.material;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.benutzerverwaltung.material.User;

/**
 * Eine Umfrage stellt eine positionsbedingte Ansammlung von Fragen an einen
 * Benutzer dar.
 */
@Entity
public class Umfrage {

	@Id
	@GeneratedValue
	private long id;

	/**
	 * Titel der Umfrage
	 */
	@Column(nullable = false)
	private String titel;

	private String kategorie;

	/**
	 * Startdatum des Zeitraumes, in welchem die Umfrage aktiv sein soll.
	 */
	private LocalDate startDatum;

	/**
	 * Enddatum des Zeitraumes, in welchem die Umfrage aktiv sein soll.
	 */
	private LocalDate endDatum;

	private LocalDate createdAt;

	private LocalDate updatedAt;

	@OneToMany(mappedBy = "umfrage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Befragung> befragungen;

	@ManyToOne
	private User ersteller;

	private boolean istMehrfachBeantwortbar;

	private boolean istDisabled;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
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

	public List<Befragung> getBefragungen() {
		return befragungen;
	}

	public void setBefragungen(List<Befragung> befragungen) {
		this.befragungen = befragungen;
	}

	public User getErsteller() {
		return ersteller;
	}

	public void setErsteller(User ersteller) {
		this.ersteller = ersteller;
	}

	public boolean getIstMehrfachBeantwortbar() {
		return istMehrfachBeantwortbar;
	}

	public void setIstMehrfachBeantwortbar(boolean istMehrfachBeantwortbar) {
		this.istMehrfachBeantwortbar = istMehrfachBeantwortbar;
	}

	public boolean getIstDisabled() {
		return istDisabled;
	}

	public void setIstDisabled(boolean istDisabled) {
		this.istDisabled = istDisabled;
	}

}
