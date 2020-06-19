package de.wps.bikehh.adminplattform.material;

public class Address {

	private String city;
	private String postcode;
	private String street;
	// not guaranteed to be present, has to be checked
	private String houseNumber;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public boolean hasHouseNumber(Address address) {
		return address.getHouseNumber() != null;
	}

}
