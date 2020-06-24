package de.wps.bikehh.umfragen.material;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity Kategorien von Meldungen / Umfragen
 *
 * @author amnesica
 *
 */

@Entity
public class Kategorie {

	@Id
	@GeneratedValue
	private int id;
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