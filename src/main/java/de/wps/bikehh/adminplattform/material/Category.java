package de.wps.bikehh.adminplattform.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity Kategorien von Meldungen / Umfragen
 *
 * @author amnesica
 *
 */
@Entity
public class Category {

	@Id
	@GeneratedValue
	private int id;

	private String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<UmfrageDB> listOfSurveys;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
	@JoinColumn(name = "parentCategory", referencedColumnName = "Id", nullable = false)
	private Category parentCategory;

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

	public List<UmfrageDB> getListOfSurveys() {
		return listOfSurveys;
	}

	public void setListOfSurveys(List<UmfrageDB> listOfSurveys) {
		this.listOfSurveys = listOfSurveys;
	}

}
