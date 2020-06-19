package de.wps.bikehh.adminplattform.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import de.wps.bikehh.benutzerverwaltung.material.User;

/**
 * Entity Umfrage
 *
 * @author amnesica
 *
 */
@Entity
public class UmfrageDB {

	/* static Survey createFromJson(String jsonString) {
		JSON j = JSON.builder().register(JacksonAnnotationExtension.std).build();
	} */

	@Id
	@GeneratedValue
	private int id;

	private double longitude;

	private double latitude;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Category.class)
	@JoinColumn(name = "category", referencedColumnName = "Id", nullable = false)
	private Category category;

	private Date createdAtDate;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> confirmedByUsers = new ArrayList<User>();

	private boolean confirmed;

	private String text;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Track.class)
	@JoinColumn(name = "track", referencedColumnName = "Id", nullable = false)
	private Track track;

	public int getId() {
		return id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getCreatedAtDate() {
		return createdAtDate;
	}

	public void setCreatedAtDate(Date createdAtDate) {
		this.createdAtDate = createdAtDate;
	}

	public List<User> getConfirmedByUsers() {
		return confirmedByUsers;
	}

	public void setConfirmedByUsers(List<User> confirmedByUsers) {
		this.confirmedByUsers = confirmedByUsers;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
}
