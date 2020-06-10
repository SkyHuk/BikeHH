package de.wps.bikehh.adfc.material;

/**
 * test class for Survey (test with JSON files)
 *
 * @author amnesica
 *
 */
public class SurveyTest {

	private int id;
	private double lng;
	private double lat;
	private String category;
	private String dateFrom;
	private String dateTo;
	private String createdAtDate;
	private String[] confirmedByUsers;
	private int confirmedThreshhold;
	private String title;
	private Question[] questions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getCreatedAtDate() {
		return createdAtDate;
	}

	public void setCreatedAtDate(String createdAtDate) {
		this.createdAtDate = createdAtDate;
	}

	public String[] getConfirmedByUsers() {
		return confirmedByUsers;
	}

	public void setConfirmedByUsers(String[] confirmedByUsers) {
		this.confirmedByUsers = confirmedByUsers;
	}

	public int getConfirmedThreshhold() {
		return confirmedThreshhold;
	}

	public void setConfirmedThreshhold(int confirmedThreshhold) {
		this.confirmedThreshhold = confirmedThreshhold;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Question[] getQuestions() {
		return questions;
	}

	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}

}
