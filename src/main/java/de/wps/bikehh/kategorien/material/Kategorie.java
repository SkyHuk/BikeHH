package de.wps.bikehh.kategorien.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Kategorie {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToOne
	private Kategorie oberKategorie;

	@OneToMany(mappedBy = "oberKategorie", cascade = CascadeType.ALL)
	private List<Kategorie> unterKategorien;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Kategorie getOberKategorie() {
		return oberKategorie;
	}

	public void setOberKategorie(Kategorie oberKategorie) {
		this.oberKategorie = oberKategorie;
	}

	public List<Kategorie> getUnterKategorien() {
		return unterKategorien;
	}

	public void setUnterKategorien(List<Kategorie> unterKategorien) {
		this.unterKategorien = unterKategorien;
	}

	public boolean isOberkategorie() {
		return oberKategorie == null;
	}

}
