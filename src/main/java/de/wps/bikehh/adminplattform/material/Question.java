package de.wps.bikehh.adminplattform.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entitiy Question
 *
 * Is used inside Survey entitiy
 *
 * @author felixwolf
 *
 */

@Entity
public class Question {

	@Id
	@GeneratedValue
	private int id;

	private String title;

	@ElementCollection
	private List<Answer> answers = new ArrayList<Answer>();

	/**
	 * dictionary that links a question and a answer, to be used as
	 * HashMap<Question, Answer>
	 *
	 * logic: the question this class is the instance of will only be asked when a
	 * specific QUESTION is answered with a specific ANSWER
	 */
	// private HashMap<String, String> conditions = new HashMap<String, String>();

	@ElementCollection
	private List<Condition> conditions = new ArrayList<Condition>();

	private boolean allowCustomAnswer;

	private double lat;

	private double lng;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/*
	 * public HashMap<String, String> getConditions() { return conditions; }
	 * 
	 * public void setConditions(HashMap<String, String> condition) {
	 * this.conditions = condition; }
	 */
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> condition) {
		this.conditions = condition;
	}

	public boolean isAllowCustomAnswer() {
		return allowCustomAnswer;
	}

	public void setAllowCustomAnswer(boolean allowCustomAnswer) {
		this.allowCustomAnswer = allowCustomAnswer;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
