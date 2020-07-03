# Bike HH - Fahrrad in Hamburg
Im Zuge des Praktikums "Fahrrad in Hamburg" der Universität Hamburg wurde ein Projekt für den ADFC (Allgemeiner Deutscher Fahrrad-Club) erstellt. Die Aufgaben dafür wurden in die Bereiche App-Entwicklung, ADFC Admin-Bereich und Account-Management aufgeteilt. Eine genaue Aufgabenbeschreibung findet sich im [Trello-Board](https://trello.com/b/PrQmuT3z/praktikum-sose-20).

## Struktur des Projekts
### Backend
* [Spring Boot Framework](https://spring.io/projects/spring-boot)
* Eine lokale H2 Datenbank

### Frontend
* [Thymeleaf](https://www.thymeleaf.org/)
* [Leaflet](https://leafletjs.com/)

## Erste Schritte

### Node
Zunächst müssen Node und Npm installiert werden. Derzeitig sind folgende Versionen installiert:
```
node: v13.2.0
npm: 6.13.1
```

Node und Npm findet ihr [hier](https://nodejs.org/en/download/). Der Download beinhaltet Node und npm. Es wird empfohlen die LTS (Long Term Support) Version zu nehmen.

### Spring Boot
Spring Boot ist in diesem Projekt bereits initialisiert. 

## Dokumentation

Für die Entwicklung an den Javascript Dateien der Admin-Platform kann folgender Trick den Workflow erhöhen:

1. Terminal-Fenster öffnen und zu ```bikehh/src/npm``` navigieren
2. den Befehl ```node_modules/.bin/webpack --watch``` laufen lassen
3. dies führt dazu, dass das Projekt nicht jedes mal neu compiliert werden muss, nachdem Änderungen an einer JS-Datei vorgenommen wurden

### Admin-Plattform
Im Folgenden wird die ADFC Admin-Platform vorgestellt und so gut es geht erläutert. Sie soll als Hilfe für das kommende Blockpraktikum, welches dieses System weiterentwickelt, dienen.

Das Projekt ist ein Maven-Projekt, welches am einfachsten mit Eclipse gebaut und entwickelt wird. Zum Bauen des Projekts kann die Datei ```BikeHH - Build.launch ``` ausgeführt werden. Das Projekt selbst wird mit ```BikehhApplication.java``` gestartet und kann im Browser auf ```http://localhost:8080/``` angesehen werden.

In der Entwicklung wurde sich am Werkzeug-Material-Ansatz (WAM-Ansatz) der Universität Hamburg orientiert.

#### Login / Logout
Daten zum Einloggen: (Genaueres ist in ```application.properties``` einsehbar)
Benutzername: ```admin@chef.lol ```
Passwort: ```admin_pw```

Neue User können manuell in der Datei ```data.sql``` in der H2 Datenbank angelegt werden.

Mit einem Klick auf ```Ausloggen``` in der Navigationsleiste kann sich ausgeloggt werden. 

#### Übersicht
In dieser Ansicht sollen einige statistische Auswertungen über die Umfragen und Meldungen erscheinen. Ergebnisse sollen geteilt und exportiert werden können.

Im Backend ist die Seite ```UebersichtController``` zu erreichen.

Diese Seite gilt es noch auszuarbeiten.

#### Umfragen
Auf der Umfragen-Seite wird eine Gesamtansicht der Umfragen als Liste dargestellt. Hier gibt es die Möglichkeit eine Einzelnansicht einer Umfrage aufzurufen, als auch die Umfragen zu löschen (Achtung: Umfrage wird hier unwiderruflich gelöscht!). 

##### UmfragenController
Für die Anzeige der Umfragenliste als auch der Einzelumfrage ist im Backend der ```UmfragenController``` zuständig, der die Umfragen über den ```UmfragenService``` aus der Datenbank bezieht. 

##### UmfragenRestController
Der ```UmfragenRestController``` ist für die Löschung einer Umfrage zuständig und nutzt dafür den ```UmfragenService```. 

Die Filterfunktion auf der Umfragen-Seite ist noch nicht ausgebaut.

##### Materialien
* **Umfrage**: Eine Umfrage besteht aus mindestens einer Frage, ist einer Kategorie zugeordnet, findet an einem Standort statt (Längen- und Breitengrad) und hat somit auch eine Adresse. Eine Umfrage hat ein Start-, End- und Erstelldatum und einen Ersteller (Admin oder Fahrradfahrer). Sie kann vom Admin bearbeitet werden. Zusätzlich zu einem Standort kann sie eine Fahrtrichtung aufweisen. Sie kann von Benutzern bestätigt werden und weißt daher einen Schwellenwert auf, wieviele Bestätigungen sie braucht.  

* **Frage**: Eine Frage gehört zu einer Umfrage, hat einen Standort, kann mehrere Antwortmöglichkeiten aufweisen und kann Bedingungen enthalten, nachdem sie nur angezeigt wird, wenn in einer vorherigen Frage eine bestimmte Antwort ausgewählt wurde. Ebenso kann sie eine benutzerdefinierte Antwort (Freitext) erlauben. Die eigentliche Frage wird als Titel der Frage spezifiziert. 

* **Adresse**: Die Adresse einer Umfrage mit Straße, Hausnummer, PLZ, Ort

* **AntwortMoeglichkeit**: Die Antwortmöglichkeit auf eine Frage

* **Bedingung**: Die Bedingung, wenn eine Frage zu einer Antwort zugewiesen wird

* **Kategorie**: Die Kategorie zu der die Umfrage gehört. Diese muss noch stark ausgebaut werden und ist momentan nur als einfacher ```String``` realisiert

##### UmfrageRepository
Das Repository für die Umfragen.

##### UmfragenService
Der Service stellt Methoden bereit, über die Umfragen aus dem Repository abgefragt und manipuliert werden können. So lassen sich hier alle oder einzelne Umfragen abfragen, als auch Umfragen aus dem Repository löschen.

#### Meldungen
TODO

#### Karte
In der Kartenansicht werden alle Umfragen aus der Datenbank angezeigt. Bei Klick auf diese können die Daten angeschaut und in die Einzel-Umfrageansicht gewechselt werden. Es besteht die Möglichkeit durch einen beliebigen Klick auf der Karte direkt an diesem Ort eine Umfrage zu erstellen oder durch den +-Button in der unteren, rechten Ecke eine Umfrage zu erstellen.  

##### KartenController
Im ```KartenController``` können alle Umfragen aus der Datenbank abgefragt werden, die in der Kartenansicht angezeigt werden sollen. 

#### Umfrage erstellen
Auf der Seite kann eine neue Umfrage erstellt werden. Dazu muss jedes Feld ausgefüllt werden und ein Standort auf der Karte ausgewählt werden. Wenn Einträge fehlen sollten oder bestimmte Bedingungen nicht erfüllt sind, wird vom Frontend eine Fehlermeldung ausgegeben und angezeigt, welches Feld noch fehlt. 

Bestimmte Bedingungen, die zum Erstellen einer Umfrage gelten müssen: 
* Startdatum darf nicht in der Vergangenheit liegen
* Enddatum darf nicht vor dem Startdatum liegen
* Titel der Umfrage darf nicht leer sein
* Kategorie darf nicht leer sein
* Es muss mindestens eine Frage geben
* Die Frage muss einen Titel aufweisen
* Die Frage muss mindestens eine Antwortmöglichkeit aufweisen oder eine Freitext-Antwort erlauben

Wenn alle Felder ausgefüllt sind wird die Umfrage an das Backend geschickt. Dort findet eine erneute Validierung statt bevor die Umfrage in der Datenbank gespeichert wird (siehe ```UmfragenErstellenRestController```).

##### UmfragenErstellenController
In diesem Controller kann der Ersteller einer Umfrage abgefragt werden, als auch eine bestimmte Umfrage abgefragt werden, welche bearbeitet werden soll.

##### UmfragenErstellenRestController
Hier wird die Umfrage gespeichert. Dazu wird sie vorher noch einmal durch den Aufruf von ```Utils.umfrageIstValide(umfrage)``` validiert und anschließend gespeichert. Dabei wird entweder die Umfrage bei einer neuen Umfrage direkt gespeichert oder es wird eine vorhandene Umfrage bearbeitet und mit ```oldUmfrage.merge(umfrage)``` zu einer Umfrage zusammengefasst. 
Wenn die Validierung schief läuft, wird eine ```ResponseStatusException``` mit dem HTTP Status ```NOT_ACCEPTABLE``` geworfen und die Umfrage nicht gespeichert. Das Frontend wird in dem Fall eine Fehlermeldung anzeigen. 

##### Utils
Die Hilfklasse ```Utils``` validiert eine Umfrage. Hierzu werden als erstes die Daten in Formate gebracht, mit denen sie verglichen werden können. Dann werden die Bedingungen, die auch im Frontend getestet werden, erneut durchgeführt. 

#### Nutzerverwaltung
Diese muss noch erstellt werden.



























