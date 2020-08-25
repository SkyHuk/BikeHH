package de.wps.bikehh.befragungen.api.dto;

public class BeantwortungDto {

	private long fragenId;

	private int antwortIndex;

	private String antwortFreitext;

	public long getFragenId() {
		return fragenId;
	}

	public void setFragenId(long fragenId) {
		this.fragenId = fragenId;
	}

	public int getAntwortIndex() {
		return antwortIndex;
	}

	public void setAntwortIndex(int antwortIndex) {
		this.antwortIndex = antwortIndex;
	}

	public String getAntwortFreitext() {
		return antwortFreitext;
	}

	public void setAntwortFreitext(String antwortFreitext) {
		this.antwortFreitext = antwortFreitext;
	}
}
