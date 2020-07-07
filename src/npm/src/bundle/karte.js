import * as L from 'leaflet'
import 'leaflet/dist/leaflet.css';

(function() {
	if (location.href.indexOf('/karte') === -1) {
		return
	}

	

	// coordinates for hamburg lat: 53.551086, long: 9.993682
	var karte = L.map('mapid').setView([53.551086, 9.993682], 13);

	// initialisiert die Karte
	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
		'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
		'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1
	}).addTo(karte);

	var popup = L.popup();

	/**
	* listener fuer das Klicken auf die Karte
	* baut poput, welches einen Verweis auf Umfrage erstellen beinhaltet
	* durch klick auf Popup kann eine Umfrage an genau dieser Stelle erstelle werden
	*/
	function onMapClick(e) {
		//create link to open "/umfrage-erstellen"
		var a = document.createElement('a');
		const laengengrad = e.latlng.lat;
		const breitengrad = e.latlng.lng;

		//text node
		const link = document.createTextNode("Hier klicken, um eine Umfrage an Position (" + laengengrad.toFixed(4) + ", " + breitengrad.toFixed(4) + ") zu erstellen");

		// Append text node to anchor element
		a.appendChild(link);

		// Set the href property. 
		a.href = "/umfrage-erstellen?koordinaten=" + [laengengrad + "," + breitengrad];

		//popup
		popup
		.setLatLng(e.latlng)
		.setContent(document.body.appendChild(a))
		.openOn(karte);
	}

	fuegeUmfrageZurKarteHinzu(umfrage);

	/**
	* fuegt eine Umfrage der Karte hinzu
	* Es wird ein Marker erstellt, der beim Klicken eine Reihe von Informationen zu der Umfrage anzeigt
	* durch klicken auf Details oder id wird man auf die Detailansicht weitergeleitet
	*/
	function fuegeUmfrageZurKarteHinzu(umfrage) {

		var bestaetigungen = "";
		if (umfrage.bestaetigtVonUsern.length > 0) {
			bestaetigungen = "Bestätigt von: " + bestaetigtVonUsern + "<br/>";
		} else {
			bestaetigungen = "Keine Bestätigungen<br/>";
		}

		// fragenHTML aufbauen
		var fragenHTML = "";
		var antwortenHTML = "";
		for (var i = 0; i < umfrage.fragen.length; i++) {
			for (var k = 0; k < umfrage.fragen[i].antwortMoeglichkeiten.length; k++) {
				if (k > 0) {
					antwortenHTML += ", ";
				}
				antwortenHTML += umfrage.fragen[i].antwortMoeglichkeiten[k].text;
			}
			var erlaubeBenutzerdefinierteAntwort = ""
			if (umfrage.fragen[i].erlaubeBenutzerdefinierteAntwort) {
				erlaubeBenutzerdefinierteAntwort = "ja" 
			} else {
				erlaubeBenutzerdefinierteAntwort = "nein"
			}
			fragenHTML += "<li>" + umfrage.fragen[i].titel + " [" + antwortenHTML + "]" + " (Freitext-Antwort: " + erlaubeBenutzerdefinierteAntwort + ")" + "</li>";
			antwortenHTML = "";
		}

		// adressHTML aufbauen
		var adresseHTML = "";
		if (umfrage.adresse.hausnummer != undefined) {
			adresseHTML = "Adresse: " + umfrage.adresse.strasse + " " + umfrage.adresse.hausnummer + ", " + umfrage.adresse.postleitZahl + ", " + umfrage.adresse.stadt + "<br/>";
		} else {
			adresseHTML = "Adresse: " + umfrage.adresse.strasse + ", " + umfrage.adresse.postleitZahl + ", " + umfrage.adresse.stadt + "<br/>";
		}

		//popup configurieren
		L.marker([umfrage.laengengrad, umfrage.breitengrad]).addTo(karte)
		.bindPopup(
			'<a href="umfragen/' + umfrage.id +'"><b>Umfrage Id ' + umfrage.id +'</b></a>' + 
			": " + umfrage.titel + "<br/>" +
			adresseHTML +
			"Kategorie: " + umfrage.kategorie.name + "<br/>" +
			"Erstellt am: " + umfrage.erstelltAmDatum + "<br/>" +
			"Dauer: " + umfrage.startDatum + " bis " + umfrage.endDatum + "<br/>" +
			bestaetigungen +
			"Fragen: " + 
			"<ul>" + fragenHTML + "</ul>" +
			'<a href="umfragen/' + umfrage.id +'">Details</b></a>'
			);
	}
	karte.on('click', onMapClick);
})()