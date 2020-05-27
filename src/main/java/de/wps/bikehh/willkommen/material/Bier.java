package de.wps.bikehh.willkommen.material;

public class Bier {

	private String marke;
	private String name;
	private double fuellmenge;

	public Bier(String marke, String name, double fuellmenge) {
		this.marke = marke;
		this.name = name;
		this.fuellmenge = fuellmenge;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFuellmenge() {
		return fuellmenge;
	}

	public void setFuellmenge(double fuellmenge) {
		this.fuellmenge = fuellmenge;
	}
}
