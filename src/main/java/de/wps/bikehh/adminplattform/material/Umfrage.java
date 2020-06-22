package de.wps.bikehh.adminplattform.material;

/**
 * Test-Klasse für eine Umfrage (mit JSON-Dateien, non-Db-Version)
 *
 * @author amnesica
 *
 */
public class Umfrage {

	private int id;
	private double laengengrad;
	private double breitengrad;
	private String kategorie;
	private String startDatum;
	private String endDatum;
	private String erstelltAmDatum;
	private String[] bestaetigtVonBenutzern;
	private int bestaetigtSchwellenwert;
	private String titel;
	private Frage[] fragen;
	private String ersteller;
	private boolean manuellErstellt;
	private Adresse adresse;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}

	public double getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public String getErstelltAmDatum() {
		return erstelltAmDatum;
	}

	public void setErstelltAmDatum(String erstelltAmDatum) {
		this.erstelltAmDatum = erstelltAmDatum;
	}

	public String[] getBestaetigtVonBenutzern() {
		return bestaetigtVonBenutzern;
	}

	public void setBestaetigtVonBenutzern(String[] bestaetigtVonBenutzern) {
		this.bestaetigtVonBenutzern = bestaetigtVonBenutzern;
	}

	public int getBestaetigtSchwellenwert() {
		return bestaetigtSchwellenwert;
	}

	public void setBestaetigtSchwellenwert(int bestaetigtSchwellenwert) {
		this.bestaetigtSchwellenwert = bestaetigtSchwellenwert;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Frage[] getFragen() {
		return fragen;
	}

	public void setFragen(Frage[] fragen) {
		this.fragen = fragen;
	}

	public String getErsteller() {
		return ersteller;
	}

	public void setErsteller(String ersteller) {
		this.ersteller = ersteller;
	}

	public boolean isManuellErstellt() {
		return manuellErstellt;
	}

	public void setManuellErstellt(boolean manuellErstellt) {
		this.manuellErstellt = manuellErstellt;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public boolean wurdeManuellErstellt() {
		return manuellErstellt;
	}

}
