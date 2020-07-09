import Vue from 'vue/dist/vue'
import * as L from 'leaflet'
import 'leaflet/dist/leaflet.css';

(function () {
  // debugger
  if (location.href.indexOf('/umfrage-erstellen') === -1) {
    return
  }
  const erstelleUmfrageApp = new Vue({
    el: '#erstelleUmfrageApp',
    data: {
      // id ist bei neuer Umfrage null und wird von der DB gesetzt,
      // wenn eine Umfrage bearbeitet wird wird die id automatisch gesetzt
      id: null,
      umfrage: umfrage,
      editierModus: false, //zeigt an, ob man sich im editieren befindet, wichtig für einige Fallunterscheidungen
      titel: '',
      kategorie: '',
      startDatum: null,
      endDatum: null,
      bestaetigtSchwellenwert: 10,
      fragen: [],
      manuellErstellt: true,
      fahrtrichtung: null,

      laengengrad: laengengrad,
      breitengrad: breitengrad,

      karte: null,
      hauptMarker: null,
      polyline: null,
      decorator: null,
      drehMarker: null,

      zeigeErfolgBenachrichtigung: false,
      formatierungsFehler: null,

      umfrageDisabled: false,

      kategorie1: null,
      kategorie2: null,
      kategorie3: null,
      benutzerdefinierteKategorie: false,
      benutzerdefinierteKategorieText: '',

      kategorieOptionen: [
        {
          id: 1,
          text: 'Hindernis',
          unteroptionen: [
          ],
        },
        {
          id: 2,
          text: 'Fahrbahnoberfläche',
          unteroptionen: [
            {
              id: 3,
              text: 'Verschmutzung',
              unteroptionen: [
                { id: 4, text: 'Schnee / Glatteis' },
              ],
            },
          ]
        },
        { id: 5, text: 'Verkehrsführung' },
        { id: 6, text: 'Abstellanlage' },
      ],
    },
    mounted() {
      // befüllt die vue.js Daten mit Informationen aus der Umfrage, wenn eine bearbeitet wird
      if (this.umfrage) {
        this.id = this.umfrage.id
        this.editierModus = true

        this.umfrage.fragen.forEach((frage) => {
          console.log('frage', frage)
          frage.bedingungen.forEach((bedingung) => {
            bedingung.frage = this.umfrage.fragen.find(f => f.id === bedingung.frageId)

            bedingung.erwarteteAntwort = this.umfrage.fragen.find(f => f.antwortMoeglichkeiten.find(moeglichkeit => moeglichkeit.id === bedingung.antwortId))

          })

        })
      }

      // needed inside leaflet callbacks where this is overwritten
      const self = this

      if (!this.editierModus) {
        this.setzeDatenZurueck()

        // init popup
        var popup = L.popup();

        // initialisiert die Karte. Default ist Hamburg, wenn man von Karte.html kommt
        // sind koordinaten gesetzt, die stattdessen verwendet werden
        if (this.laengengrad == 0 && this.breitengrad == 0) {
          this.karte = L.map('mapid').setView([53.551086, 9.993682], 12);
        } else {
          this.karte = L.map('mapid').setView([this.breitengrad, this.laengengrad], 16);
        }

        this.setzeKarteZuruek()

        this.initialisiereKarte()

      } else {
        this.laengengrad = this.umfrage.laengengrad
        this.breitengrad = this.umfrage.breitengrad
        this.karte = L.map('mapid').setView([this.breitengrad, this.laengengrad], 16)

        this.initialisiereKarte()

        var marker = L.marker([this.breitengrad, this.laengengrad])
          .addTo(this.karte)
          .bindPopup("<b>Ort der Umfrage</b><br/>")
          .on('drag', self.setzteHauptmarkerZurueck)

        this.hauptMarker = marker
        //console.log('titel:', this.umfrage.titel)
        this.titel = this.umfrage.titel
        this.kategorie = this.umfrage.kategorie
        this.startDatum = this.umfrage.startDatum
        this.endDatum = this.umfrage.endDatum
        this.bestaetigtSchwellenwert = this.umfrage.bestaetigtSchwellenwert
        this.manuellErstellt = this.umfrage.manuellErstellt
        //console.log('umfrageDisablend:', this.umfrage.umfrageDisabled)
        this.umfrageDisabled = this.umfrage.umfrageDisabled
        this.fahrtrichtung = this.umfrage.fahrtrichtung
        if (this.fahrtrichtung) {
          const markerKoordinaten = marker.getLatLng()
          this.fuegeMarkerFahrtrichtungHinzu(markerKoordinaten.lat, markerKoordinaten.lng, this.fahrtrichtung + (Math.PI / 2))
        }

        this.umfrage.fragen.forEach((frage) => {
          if (frage.laengengrad && frage.breitengrad) {
            this.fuegeOrtHinzu(frage)

            if (frage.fahrtrichtung !== null) {
              this.fuegeMarkerFahrtrichtungHinzu(frage.breitengrad, frage.laengengrad, frage.fahrtrichtung + (Math.PI / 2), frage)
            }

          }

        })

        this.fragen = this.umfrage.fragen
      }

      if (!this.hauptMarker) {
        this.karte.on('click', function (e) {
          const breitengrad = e.latlng.lat
          const laengengrad = e.latlng.lng

          // add marker to map
          var marker = L.marker([breitengrad, laengengrad], {
            draggable: true,
            autoPan: true
          })
            .addTo(self.karte)
            .bindPopup("<b>Hier wird die erstellte Umfrage sein.</b><br/>")
            .on('drag', self.setzteHauptmarkerZurueck);

          // entferne alten Marker wenn vorhanden
          if (self.hauptMarker) { self.hauptMarker.remove() }
          self.hauptMarker = marker

          self.karte.off('click')

          // reset Fahrtrichtung
          if (self.fahrtrichtung) {
            self.setzteHauptmarkerZurueck()
          }

        })
      }
    },
    created() {
    },
    methods: {
    	/**
    	 * fügt eine Frage dem Formular hinzu mit Standardantwortmoeglichkeiten
    	 */
      fuegeFrageHinzu() {
        this.fragen.push({
          titel: '',
          antwortMoeglichkeiten: [
            { text: 'Ja', },
            { text: 'Nein', },
          ],
          bedingungen: [],
          marker: null,
          fahrtrichtung: null,
          fahrtrichtungGesetzt: false,
          pfeillinie: null,
          pfeilspitze: null,
          drehMarker: null,
          erlaubeBenutzerdefinierteAntwort: this.erlaubeBenutzerdefinierteAntwort ? true : false
        })
      },
      /**
       * löscht eine Frage vom Formular
       */
      entferneFrage(frage) {
        var index = this.fragen.indexOf(frage);
        this.fragen.splice(index, 1);
      },
      /**
       * fügt einer Frage eine Antwortmoeglichkeit hinzu
       */
      fuegeAntwortMoeglichkeitHinzu(frage) {
        frage.antwortMoeglichkeiten.push({ text: '' });
      },
      /**
       * löscht eine Antwortmoeglichkeit an einer Frage
       */
      entferneAntwort(frage, antwortMoeglichkeit) {
        frage.antwortMoeglichkeiten.splice(frage.antwortMoeglichkeiten.indexOf(antwortMoeglichkeit), 1)
      },
      /**
       * fügt einer Frage einen Ort hinzu (grüner Marker)
       * bricht ab, wenn Frage bereits einen Marker hat
       */
      fuegeOrtHinzu(frage) {
        var bestehenderMarker = frage.marker;
        if (!bestehenderMarker) {
          var gruenesIcon = new L.Icon({
            iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
            shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
            iconSize: [25, 41],
            iconAnchor: [12, 41],
            popupAnchor: [1, -34],
            shadowSize: [41, 41]
          });
          const index = this.fragen.indexOf(frage) + 1;

          let koordinaten
          if (frage.laengengrad && frage.breitengrad) {
            koordinaten = [frage.breitengrad, frage.laengengrad]

          } else {
            const grenzen = this.karte.getBounds();
            koordinaten = grenzen.getCenter();

          }

          var marker = L.marker(koordinaten, {
            draggable: true,
            autoPan: true,
            icon: gruenesIcon
          }).addTo(this.karte)

          // only show popup when adding new location
          if (!frage.laengengrad && !frage.laengengrad) {
            marker.bindPopup('<b>Verschieb mich!</b>').openPopup();
          }

          marker.on('dragend', function (event) {
            marker.bindPopup('<b>Ort für Frage ' + index + ' </b>');
          });

          marker.on('drag', () => {
            if (frage.fahrtrichtung) {
              frage.pfeillinie.remove()
              frage.pfeilspitze.remove()
              const markerKoordinaten = marker.getLatLng()
              this.fuegeMarkerFahrtrichtungHinzu(markerKoordinaten.lat, markerKoordinaten.lng, frage.fahrtrichtung + (Math.PI / 2), frage)
            }
          })
          frage.marker = marker;

        } else {
          bestehenderMarker.openPopup();

        }
      },
      /**
       * entfernt einen Ort einer Frage.
       * Wenn der Ort eine Fahrtrichtung hat wird diese auch gelöscht
       */
      entferneOrt(frage) {
        var marker = frage.marker;
        if (marker) {
          marker.remove();
          frage.marker = null;
          if (frage.pfeillinie) {
            this.entferneFahrtrichtung(frage)
          }
        }
      },
      /**
       * fügt einer Frage eine Bedingungen hinzu
       */
      fuegeBedingungHinzu(frage) {
        frage.bedingungen.push({
          frage: null,
          erwarteteAntwort: null,
        })
      },
      /**
       * löscht eine Bedingung einer Frage
       */
      entferneBedingung(frage, bedingung) {
        frage.bedingungen.splice(frage.bedingungen.indexOf(bedingung), 1)

      },
      /**
       * überprüft, ob alle Fragen gültig sind.
       * Um als gültig zu gelten muss jede Frage mindestens eine AntwortMoeglichkeit haben
       * oder Freitext erlauben
       */
      sindAlleFragenGueltig(fragen) {

        // prüfen, ob alle Fragen mind. eine Antwortmoeglichkeit hat
        const habenAlleFragenAntwortMoeglichkeiten = fragen.filter(q => q.antwortMoeglichkeiten.length > 0).length === fragen.length

        //prüfen, ob Fragen ohne AntwortMoeglichkeit benutzerdefinierte Antworten erlauben
        const fragenOhneAntwortMoeglichkeit = fragen.filter(q => q.antwortMoeglichkeiten.length < 1)
        var erlaubenAlleFragenOhneAntwortMoeglichkeitenBenutzerdefinierteAntworten = true
        fragenOhneAntwortMoeglichkeit.forEach((frage) => {
          if (!frage.erlaubeBenutzerdefinierteAntwort) {
            erlaubenAlleFragenOhneAntwortMoeglichkeitenBenutzerdefinierteAntworten = false
          }
        })
        return habenAlleFragenAntwortMoeglichkeiten || erlaubenAlleFragenOhneAntwortMoeglichkeitenBenutzerdefinierteAntworten

      },
      /**
       * überprüft, ob alle Fragen einen Titel haben
       */
      habenAlleFragenTitel(fragen) {
        return fragen.filter(q => !!q.titel && q.titel.trim().length > 0).length === fragen.length

      },
      /**
       * setzt die Karte zurück: löscht alle Marker der Umfrage (Hauptmarker und Fragenmarker)
       */
      setzeKarteZuruek() {
        if (this.hauptMarker) { this.hauptMarker.remove() }
        this.fragen.forEach((frage) => {
          if (frage.marker) {
            frage.marker.remove()
          }
        });

        if (this.laengengrad && this.breitengrad) {
          var marker = L.marker([this.breitengrad, this.laengengrad]).addTo(this.karte)
            .bindPopup("<b>Hier wird die erstellte Umfrage sein.</b><br/>");
          this.hauptMarker = marker
        }
      },
      /**
       * setzt alle vuejs Daten zurück
       */
      setzeDatenZurueck() {
        this.titel = ''
        this.kategorie = ''
        this.startDatum = null
        this.endDatum = null
        this.bestaetigtSchwellenwert = 10
        this.fragen = []
        this.manuellErstellt = true
        this.kategorie1 = null
        this.kategorie2 = null
        this.kategorie3 = null
        this.benutzerdefinierteKategorie = false
        this.benutzerdefinierteKategorieText = null
        this.hauptMarker = null
        this.polyline = null
        this.decorator = null
        this.umfrageDisabled = false

        // fügt initial eine Frage zur umfrage hinzu
        this.fuegeFrageHinzu()
      },
      /**
       * leitet den Vorgang des Umfrage-Abschickens ein
       */
      postUmfrage(e) {
        e.preventDefault()
        // data object mit allen Information der Umfrage
        var data = {};
        data = this.sammleDaten(data)
        if (data) {
          if (this.validiereDaten(data)) {
            this.post(data)
          }
        }
      },
      /**
       * holt sich über OSM API Adresse zu gegebenen Koordinaten, ruft dann postJson() mit den Daten auf
       */
      post(data) {
        const getRequest = new XMLHttpRequest();
        getRequest.open("GET", 'https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=' + data.laengengrad + '&lon=' + data.breitengrad);
        getRequest.responseType = "json";
        const self = this;
        // make post with complete json to controller once address is fetched
        getRequest.onload = function (e) {
          if (getRequest.status == 200) {
            var adresse = new Object();
            data.adresse = new Object();
            // city is something different depending on the location [CITY, VILLAGE, TOWN]
            if (getRequest.response.address.city != undefined) {
              adresse.stadt = getRequest.response.address.city;
            } else if (getRequest.response.address.village != undefined) {
              adresse.stadt = getRequest.response.address.village;
            } else if (getRequest.response.address.town != undefined) {
              adresse.stadt = getRequest.response.address.town;
            }
            adresse.postleitZahl = getRequest.response.address.postcode;
            adresse.strasse = getRequest.response.address.road;
            adresse.hausnummer = getRequest.response.address.house_number; // not guaranteed to be present!
            data.adresse = adresse;
            self.postJson(data)
          }
        }

        getRequest.send();
      },
      /**
       * schickt die Umfrage ans Backend
       */
      postJson(data) {
        const self = this;
        const jsonString = JSON.stringify(data);
        const request = new XMLHttpRequest();
        request.open("POST", "/umfrage-erstellen");
        request.setRequestHeader("Content-Type", "application/json");
        request.send(jsonString);
        request.onload = function (e) {
          if (request.status == 200) {
            self.behandelErfolg();
            self.id = request.response
          } else {
            self.behandelFehler(null, "server");
          }
        }
      },
      /**
       * sammelt alle Daten der Umfrage abhängig davon, ob es eine neue Umfrage ist oder nicht
       */
      sammleDaten(data) {

        if (!this.editierModus) {
          data.bestaetigtVonUsern = []
          data.ersteller = ersteller
          const zeitstempel = Number(new Date())
          const date = new Date(zeitstempel)
          data.erstelltAmDatum = date;
          // umfrage ist immer enabled, wenn sie erstellt wird
          data.umfrageDisabled = false;

        } else {
          data.bestaetigtVonUsern = this.umfrage.bestaetigtVonUsern
          data.ersteller = this.umfrage.ersteller
          data.erstelltAmDatum = this.umfrage.erstelltAmDatum
          data.umfrageDisabled = this.umfrageDisabled

        }

        if (this.id) {
          data.id = this.id

        }
        data.fahrtrichtung = this.fahrtrichtung ? this.fahrtrichtung : null
        data.bearbeitet = this.editierModus
        data.manuellErstellt = this.manuellErstellt
        data.titel = this.titel;
        data.startDatum = this.startDatum;
        data.endDatum = this.endDatum;
        data.bestaetigtSchwellenwert = this.bestaetigtSchwellenwert;

        // schreibt alle Umfrage-relevanten Informationen aus den Fragen in das data Object
        data.fragen = this.fragen.map((frage) => {
          var rtn = {
            ...frage,
            bedingungen: frage.bedingungen.map((bedingung) => {
              return {
                frage: bedingung.frage.titel,
                erwarteteAntwort: bedingung.erwarteteAntwort.text
              }
            })
          }

          if (frage.marker) {
            rtn.breitengrad = frage.marker.getLatLng().lat;
            rtn.laengengrad = frage.marker.getLatLng().lng;
          }
          if (!frage.fahrtrichtung) {
            rtn.fahrtrichtung = null
          }

          delete rtn.drehMarker
          delete rtn.marker
          delete rtn.pfeillinie
          delete rtn.pfeilspitze
          return rtn;
        });

        if (this.benutzerdefinierteKategorie) {
          // TODO: creates new kategorie serverside
          data.kategorie = { name: this.benutzerdefinierteKategorieText }

        } else {
          if (!this.kategorie1) {
            this.behandelFehler('Kategorie nicht ausgewählt')
            return false
          }

          // TODO: find kategorie recursively
          data.kategorie = { name: this.kategorie3 ? this.kategorie3.text : this.kategorie2 ? this.kategorie2.text : this.kategorie1.text }

        }

        // ohne Hauptmarker keine Koordinaten
        if (!this.hauptMarker) {
          this.behandelFehler('Ein Ort muss ausgewählt sein')
          return false
        }

        // Hauptmarker ist gesetzt, hole Koordinaten
        data.breitengrad = this.hauptMarker.getLatLng().lat;
        data.laengengrad = this.hauptMarker.getLatLng().lng;
        return data;
      },
      /**
       * zeigt eine Erfolgsnachricht, versteckt alle Error-Nachrichten wenn vorhanden
       */
      behandelErfolg() {
        this.zeigeErfolgBenachrichtigung = true
        //delete all error modals
        this.formatierungsFehler = null;

        if (!this.editierModus) {


          //reset all fields
          this.setzeDatenZurueck();
          //reset map
          this.setzeKarteZuruek();
          this.laengengrad = null;
          this.breitengrad = null;
          this.karte = null;
        } else {

        }
      },
      /**
       * zeigt eine unterschiedliche Fehlermeldung abhängig von der Art des Fehlers
       */
      behandelFehler(text, typ) {
        if (typ == "server") {
          this.formatierungsFehler = 'Es ist ein Fehler beim kommunizieren mit dem Server aufgetreten.';
          this.zeigeErfolgBenachrichtigung = false;
        } else {
          // show negative modal and hide positive modal
          this.formatierungsFehler = text || 'Alle Felder müssen ausgefüllt werden und ein Punkt auf der Karte angegeben werden. Das Startdatum darf nicht in der Vergangenheit liegen.'
          this.zeigeErfolgBenachrichtigung = false
        }
      },
      /**
       * prüft, ob alle benötigten Daten verfügbar sind
       */
      validiereDaten(data) {
        //TODO kategorie1 test
        if (data.titel.trim().length === 0) {
          this.behandelFehler('Titel muss ausgefüllt sein')
          return
        }

        if (data.startDatum === null ||
          data.endDatum === null) {
          this.behandelFehler('Das Zeitfenster muss ausgefüllt sein.')
          return
        }

        if (this.startDatum < new Date().toJSON().slice(0, 10) && !data.bearbeitet) {
          this.behandelFehler('Das Startdatum darf nicht in der Vergangenheit liegen.')
          return
        }

        if (this.startDatum > this.endDatum) {
          this.behandelFehler('Das Enddatum darf nicht vor dem Startdatum liegen.')
          return
        }

        if (!data.bestaetigtSchwellenwert ||
          !data.fragen ||
          !this.habenAlleFragenTitel(data.fragen) ||
          !this.sindAlleFragenGueltig(data.fragen) ||
          data.erstelltAmDatum === null) {
          this.behandelFehler()
          return
        }
        return data;
      },
      /**
       * initialisiert Karte
       */
      initialisiereKarte() {
        // init map
        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
          maxZoom: 18,
          attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
          id: 'mapbox/streets-v11',
          tileSize: 512,
          zoomOffset: -1
        }).addTo(this.karte);
      },
      /**
       * fügt eine Fahrtrichtung zu einem Marker hinzu
       * Wenn Frage nicht gesetzt ist wird der Hauptmarker genommen,
       * sonst der Marker der Frage
       */
      fuegeMarkerFahrtrichtungHinzu(lat, lng, initialerWinkel, frage) {
        if (initialerWinkel === null || initialerWinkel === undefined) {
          initialerWinkel = Math.PI / 4
          if (frage) {
            frage.fahrtrichtung = initialerWinkel - (Math.PI / 2)

          } else {
            this.fahrtrichtung = initialerWinkel - (Math.PI / 2)

          }
        }

        if (frage) {
          frage.fahrtrichtungGesetzt = true
        }

        const pfeilLaenge = 0.002
        const halbePfeilLaenge = pfeilLaenge / 2
        const drehMarkerOffset = 0.0005

        let gedrehtesX = (Math.cos(initialerWinkel) * halbePfeilLaenge * 2) // - (Math.sin(initialerWinkel) * initialY)
        const gedrehtesY = (Math.sin(initialerWinkel) * halbePfeilLaenge) // + (Math.cos(initialerWinkel) * initialY)

        // skalieren auf der x Achse je nach Winkel um Länge des Pfeils angenähert zu erhalten
        // gedrehtesX *= Math.abs(Math.cos(initialerWinkel) * 2)

        let polyline = L.polyline([
          [lat - gedrehtesY, lng - gedrehtesX],
          [lat + gedrehtesY, lng + gedrehtesX],
        ], {
          weight: 2,
        }).addTo(this.karte)

        // this.markers.addLayer(polyline)

        let decorator = L.polylineDecorator(polyline, {
          patterns: [
            { offset: '100%', repeat: 0, symbol: L.Symbol.arrowHead({ pixelSize: 20, polygon: false, pathOptions: { stroke: true } }) },
          ]
        }).addTo(this.karte)

        // this.markers.addLayer(decorator)

        const onRotate = (e) => {
          const x = e.latlng.lat - lat
          const y = e.latlng.lng - lng
          const angle = Math.atan2(x, y)

          // rotiere 90° nach links
          if (frage) {
            frage.fahrtrichtung = angle - (Math.PI / 2)
          } else {
            this.fahrtrichtung = angle - (Math.PI / 2)
          }

          // arrow pointing straight right
          const initialX = halbePfeilLaenge
          const initialY = 0.0

          let gedrehtesX = (Math.cos(angle) * initialX * 2) // - (Math.sin(angle) * initialY)
          const gedrehtesY = (Math.sin(angle) * initialX) // + (Math.cos(angle) * initialY)

          // skalieren auf der x Achse je nach Winkel um Länge des Pfeils angenähert zu erhalten
          // gedrehtesX *= Math.abs(Math.sin(angle) * 2)

          // console.log('length after rotate', Math.sqrt(Math.pow(gedrehtesX, 2) + Math.pow(gedrehtesY, 2)))

          const newStart = [
            lat - gedrehtesY,
            lng - gedrehtesX,
          ]

          const newEnd = [
            lat + gedrehtesY,
            lng + gedrehtesX,
          ]

          polyline.setLatLngs([newStart, newEnd])

          decorator.remove()
          decorator = L.polylineDecorator(polyline, {
            patterns: [
              { offset: '100%', repeat: 0, symbol: L.Symbol.arrowHead({ pixelSize: 20, polygon: false, pathOptions: { stroke: true } }) },
            ]
          }).addTo(this.karte)
          if (frage) {
            frage.pfeilspitze = decorator
          } else {
            this.decorator = decorator
          }
        }

        let drehMarker

        const fuegeDrehmarkerHinzu = (drehMarkerPos) => {
          var gelbesIcon = new L.Icon({
            iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-yellow.png',
            shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
            iconSize: [25, 41],
            iconAnchor: [12, 41],
            popupAnchor: [1, -34],
            shadowSize: [41, 41]
          });

          drehMarker = L.marker(drehMarkerPos, {
            draggable: true,
            autoPan: false,
            icon: gelbesIcon
          }).addTo(this.karte).on('drag', onRotate)
          if (frage) {
            frage.drehMarker = drehMarker
          } else {
            this.drehMarker = drehMarker
          }
        }

        let drehMarkerPos = [
          lat + ((halbePfeilLaenge + drehMarkerOffset) * Math.sin(initialerWinkel)),
          lng + ((halbePfeilLaenge + drehMarkerOffset) * Math.cos(initialerWinkel)),

        ]

        if (frage && frage.drehMarker) {
          frage.drehMarker.off('drag')
          frage.drehMarker.on('drag', onRotate)
          frage.drehMarker.setLatLng(drehMarkerPos)
        } else if (!frage && this.drehMarker) {
          this.drehMarker.off('drag')
          this.drehMarker.on('drag', onRotate)
          this.drehMarker.setLatLng(drehMarkerPos)
        } else {
          fuegeDrehmarkerHinzu(drehMarkerPos)
        }

        if (frage) {
          frage.pfeillinie = polyline
          frage.pfeilspitze = decorator
        } else {
          this.polyline = polyline
          this.decorator = decorator
        }
        return polyline
      },
      /**
       * versteckt die Fehlermeldung
       */
      entferneFehlermeldung() {
        this.formatierungsFehler = null
      },
      /**
       * setzt den Hauptmarker zurück, mit ihm auch seine Fahrtrichtung (wenn gesetzt)
       */
      setzteHauptmarkerZurueck(e) {
        if (this.fahrtrichtung) {
          // this.entferneFahrtrichtung()
          this.polyline.remove()
          this.decorator.remove()
          const markerKoordinaten = this.hauptMarker.getLatLng()
          this.fuegeMarkerFahrtrichtungHinzu(markerKoordinaten.lat, markerKoordinaten.lng, this.fahrtrichtung + (Math.PI / 2))
        }
      },
      /**
       * fügt hinzu oder löscht die Fahrtrichtung eines Markers.
       * Wenn Frage nicht gesetzt wird Hauptmarker genommen, sonst Marker der Frage
       */
      setzeFahrtrichtung(frage) {
        if (!frage) { //soll wohl der hauptMarker sein
          if (this.hauptMarker) {
            if (this.fahrtrichtung === null) {
              const markerKoordinaten = this.hauptMarker.getLatLng()
              this.fuegeMarkerFahrtrichtungHinzu(markerKoordinaten.lat, markerKoordinaten.lng)
            } else {
              this.entferneFahrtrichtung()
            }
          } else {
            //hauptMarker nicht gesetzt
            this.behandelFehler('Es muss ein Ort ausgewählt sein um eine Fahrtrichtung zu setzen.')
            var self = this
            setInterval(function () { self.entferneFehlermeldung() }, 3000);
          }
        } else {
          if (frage.pfeillinie) {
            this.entferneFahrtrichtung(frage)
          } else {
            this.fuegeMarkerFahrtrichtungHinzu(frage.marker.getLatLng().lat, frage.marker.getLatLng().lng, undefined, frage)
          }
        }

      },
      /**
       * entfernt die Fahrtrichtung einer Frage oder des Hauptmarkers.
       */
      entferneFahrtrichtung(frage) {
        if (!frage) {
          this.fahrtrichtung = null

          this.polyline.remove()
          this.polyline = null
          this.decorator.remove()
          this.decorator = null
          this.drehMarker.remove()
          this.drehMarker = null
        } else {
          frage.fahrtrichtung = null

          frage.pfeillinie.remove()
          frage.pfeillinie = null
          frage.pfeilspitze.remove()
          frage.pfeilspitze = null
          frage.drehMarker.remove()
          frage.drehMarker = null
        }
      },
    },
  })
})()
