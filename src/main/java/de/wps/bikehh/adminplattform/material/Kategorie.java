package de.wps.bikehh.adminplattform.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "kategorie", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<UmfrageDB> umfragenListe;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Kategorie.class)
	@JoinColumn(name = "parentCategory", referencedColumnName = "Id", nullable = false)
	private Kategorie oberKategorie;

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

	public List<UmfrageDB> getUmfragenListe() {
		return umfragenListe;
	}

	public void setUmfragenListe(List<UmfrageDB> umfragenListe) {
		this.umfragenListe = umfragenListe;
	}

}
