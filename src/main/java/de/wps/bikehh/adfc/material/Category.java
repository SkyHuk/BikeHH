package de.wps.bikehh.adfc.material;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Survey> listOfSurveys;
}
