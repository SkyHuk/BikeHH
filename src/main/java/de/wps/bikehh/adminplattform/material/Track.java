package de.wps.bikehh.adminplattform.material;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity Strecke
 *
 * @author amnesica
 *
 */
@Entity
public class Track {

	@Id
	@GeneratedValue
	private int id;

	private double latitude;

	private double longitude;

	private Date createdAtDate;

	@OneToMany(mappedBy = "track", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<UmfrageDB> listOfSurveys;

	public int getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getCreatedAtDate() {
		return createdAtDate;
	}

	public void setCreatedAtDate(Date createdAtDate) {
		this.createdAtDate = createdAtDate;
	}

}
