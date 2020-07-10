import * as L from 'leaflet'
import 'leaflet/dist/leaflet.css';

(function () {
  if (location.href.indexOf('/umfragen/') === -1) {
    return
  }
  // Koordinaten speichern vom spring controller
  var breitengrad = umfrage.breitengrad
  var laengengrad = umfrage.laengengrad

  if (umfrage.fahrtrichtung === 0.0) {
    umfrage.fahrtrichtung = null
  }

  //Karteansicht mit den Koordinaten konfigurieren
  var karte = L.map('mapid').setView([breitengrad, laengengrad], 13);

  //Marker hinzufügen
  fuegeMarkerZurKarteHinzu(breitengrad, laengengrad);

  // Fahrtrichtungen hinzufügen
  if (umfrage.fahrtrichtung) {
    fuegeFahrtrichtungHinzu(breitengrad, laengengrad, umfrage.fahrtrichtung);
  }

  // karte initialisieren
  L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
      '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
      'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1
  }).addTo(karte);

  // alle Fragenmarker hinzufügen
  umfrage.fragen.forEach((frage, index) => {
    if (frage.fahrtrichtung === 0.0) {
      frage.fahrtrichtung = null
    }
    fuegeFragenmarkerZurKarteHinzu(frage, index)
  })

  /**
   * fügt alle Marker der Fragen hinzu
   */
  function fuegeFragenmarkerZurKarteHinzu(frage, index) {
    if (frage.breitengrad && frage.laengengrad) {
      var greenIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
      });
      var marker = L.marker([frage.breitengrad, frage.laengengrad], {
        icon: greenIcon
      }).addTo(karte).bindPopup('<b>Ort für Frage ' + index + ': ' + frage.titel + '</b>');

      if (frage.fahrtrichtung) {
        fuegeFahrtrichtungHinzu(frage.breitengrad, frage.laengengrad, frage.fahrtrichtung, frage)
      }
    }
  }

  /**
   * fügt zu gegebenen Koordinaten einen Marker hinzu
   */
  function fuegeMarkerZurKarteHinzu(breitengrad, laengengrad) {
    var marker = L.marker([breitengrad, laengengrad]).addTo(karte)
      .bindPopup("<b>Ort der Umfrage.<br/>");
  };

  /**
   * fügt eine Fahrtrichtung an gegebenen Koordinaten mit einem initialen Winkel hinzu
   */
  function fuegeFahrtrichtungHinzu(lat, lng, initialerWinkel, frage) {
    if (initialerWinkel === null) {
      initialerWinkel = Math.PI / 4
    }

    const pfeilLaenge = 0.002
    const halbePfeilLaenge = pfeilLaenge / 2
    const drehMarkerOffset = 0.0005

    let gedrehtesX = (Math.cos(initialerWinkel) * halbePfeilLaenge * 2) // - (Math.sin(initialerWinkel) * initialY)
    const gedrehtesY = (Math.sin(initialerWinkel) * halbePfeilLaenge) // + (Math.cos(initialerWinkel) * initialY)

    // skalieren auf der x Achse je nach Winkefl um Länge des Pfeils angenähert zu erhalten
    // gedrehtesX *= Math.abs(Math.cos(initialerWinkel) * 2)

    let polyline = L.polyline([
      [lat - gedrehtesY, lng - gedrehtesX],
      [lat + gedrehtesY, lng + gedrehtesX],
    ], {
      weight: 2,
    }).addTo(karte)

    // this.markers.addLayer(polyline)

    let decorator = L.polylineDecorator(polyline, {
      patterns: [
        { offset: '100%', repeat: 0, symbol: L.Symbol.arrowHead({ pixelSize: 20, polygon: false, pathOptions: { stroke: true } }) },
      ]
    }).addTo(karte)

  }

  // zentriert die Karte um die Umfrage, zoomt rein für bessere Sichtbarkeit
  function zeigAufKarte() {
    karte.setView([breitengrad, laengengrad], 17);
  };

  // Öffnet den Standard E-Mail Client des Benutzers und erstellt eine neue E-Mail, Betreff und Empfänger sind schon ausgefüllt.
  // Idee: bei Rückfragen zu dieser Umfrage kann der Ersteller kontaktiert werden
  function kontaktiereErsteller(ersteller) {
    var mailAdresse = ersteller
    var betreff = "BikeHH - Umfrage #" + umfrage.id;
    window.location.href = "mailto:" + mailAdresse + "?subject=" + betreff + "&body=";
  }

  // löscht die Umfrage über delete request an backend
  function loescheUmfrage(id) {
    const request = new XMLHttpRequest();
    request.open("DELETE", "/umfragen/delete/" + id);
    request.setRequestHeader("Content-Type", "application/json");
    request.send();
    console.log(request)
    request.onload = function (e) {
      if (request.status == 200) {
        location.href = "umfragen/"
      }
    }
  }

  //window onload Funktionen
  window.onload = function () {
    var buttonZeigAufKarte = document.getElementById("buttonZeigAufKarte");
    buttonZeigAufKarte.onclick = zeigAufKarte;

    var buttonKontaktiereErsteller = document.getElementById("buttonKontaktiereErsteller");
    buttonKontaktiereErsteller.onclick = kontaktiereErsteller;

    var buttonLoescheUmfrage = document.getElementById("buttonLoescheUmfrage");
    var functionOeffneDialogUndLoescheUmfrage = function oeffneDialog() {
      if (confirm("Soll die Umfrage wirklich gelöscht werden?")) {
        loescheUmfrage(umfrage.id)
      }
    }
    buttonLoescheUmfrage.onclick = functionOeffneDialogUndLoescheUmfrage;

    var buttonDisableEnableUmfrage = document.getElementById("buttonDisableEnableUmfrage");
    var functionOeffneDialogUndDisableEnableUmfrage = function oeffneDialogDisablen() {
      var aktiviertOderDeaktiviert = 'deaktiviert'
      if (umfrage.umfrageDisabled) {
        aktiviertOderDeaktiviert = 'aktiviert'
      }

      if (confirm("Soll die Umfrage wirklich " + aktiviertOderDeaktiviert + " werden?")) {
        deaktiviereOderAktiviereUmfrage(umfrage.id)
      }

    }
    buttonDisableEnableUmfrage.onclick = functionOeffneDialogUndDisableEnableUmfrage;
  }

  // deaktiviert die Umfrage über patch request an backend
  function deaktiviereOderAktiviereUmfrage(id) {
    const request = new XMLHttpRequest();
    request.open("PATCH", "/umfragen/disable/" + id);
    request.setRequestHeader("Content-Type", "application/json");
    request.send();
    console.log(request)
    request.onload = function (e) {
      if (request.status == 200) {
        location.href = "umfragen/" + umfrage.id
      }
    }
  }
})()