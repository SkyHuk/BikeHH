package de.wps.bikehh.adfc.material;

import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<String, String> condition = new HashMap<String, String>();

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

	public HashMap<String, String> getCondition() {
		return condition;
	}

	public void setCondition(HashMap<String, String> condition) {
		this.condition = condition;
	}

}
