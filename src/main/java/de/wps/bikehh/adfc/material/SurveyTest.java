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
	private String type;
	private String createdAtDate;
	private String confirmedByUsers;
	private boolean confirmed;
	private String text;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatedAtDate() {
		return createdAtDate;
	}

	public void setCreatedAtDate(String createdAtDate) {
		this.createdAtDate = createdAtDate;
	}

	public String getConfirmedByUsers() {
		return confirmedByUsers;
	}

	public void setConfirmedByUsers(String confirmedByUsers) {
		this.confirmedByUsers = confirmedByUsers;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
