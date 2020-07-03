package de.wps.bikehh.umfragen.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Datenbank entity
 * 
 * Kategorien von Meldungen / Umfragen
 *
 * TODO: Kategorien sind noch nicht so implementiert wie sie sollen. Momentan
 * wird nur ein String gespeichert, es sollten aber vordefinierte, Baum-artige
 * Strukturen von Kategorien geben, aus denen gewählt werden kann
 * (oberKategorie, tree traverse up). Andererseits sollen auch
 * benutzerdefinierte Kategorien möglich sein
 *
 */

@Entity
public class Kategorie {

	@Id
	@GeneratedValue
	private int id;

	// temporary solution, saves the kategorie in a simple string
	private String name;

	// @OneToMany(mappedBy = "kategorie", fetch = FetchType.LAZY, cascade =
	// CascadeType.ALL)
	// private List<Umfrage> umfragenListe;
	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	// @JoinColumn(name = "parentCategory", referencedColumnName = "Id", nullable =
	// true)
	// @Column(nullable = true)
	// private Kategorie oberKategorie;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}