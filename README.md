# Bike HH - Fahrrad in Hamburg
Im Zuge des Praktikums "Fahrrad in Hamburg" der Universität Hamburg wurde ein Projekt für den ADFC (Allgemeiner Deutscher Fahrrad-Club) erstellt. Die Aufgaben dafür wurden in die Bereiche App-Entwicklung, ADFC Admin-Bereich und Account-Management aufgeteilt. Eine genaue Aufgabenbeschreibung findet sich im [Trello-Board](https://trello.com/b/PrQmuT3z/praktikum-sose-20).

## Inhaltsverzeichnis
- [Struktur des Projeks](#struktur-des-projekts)
	- [Backend](#Backend)
	- [Frontend](#frontend)
- [Erste Schritte](#erste-schritte)
	- [Node](#node)
	- [Spring Boot](#spring-boot)
	- [Entwicklungstipps](#entwicklungstipps)
- [Dokumentation](#dokumentation)
	- [Admin-Plattform](#admin-plattform)
		- [ToDos](#todos)
		- [Architektur](#architektur)
		- [Login / Logout](#login-logout)
		- [Übersicht](#bersicht)
		- [Umfragen](#umfragen)
		- [Meldungen](#meldungen)
		- [Karte](#karte)
		- [Fahrtrichtungen](#fahrtrichtungen)
		- [Umfrage erstellen](#umfrage-erstellen)
		- [Tests](#tests)
	- [Nutzerverwaltung](#nutzerverwaltung)
	    - [Allgemeines](#allgemeines)
	    - [API-Referenz](#api-referenz)
	    - [Authentifizierung & Autorisierung](#authentifizierung&autorisierung)
	    - [Übersicht](#bersicht)
	    
	    


## Struktur des Projekts
### Backend
* [Spring Boot Framework](https://spring.io/projects/spring-boot)
* Eine lokale H2 Datenbank

### Frontend
* [Thymeleaf](https://www.thymeleaf.org/) für das generieren von HTML
* [Leaflet](https://leafletjs.com/) für die Karte
* [Vue.js](https://vuejs.org/) in ```/umfrage-erstellen``` für dynamische Formulare

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

### Entwicklungstipps

```
mvn compile -P eclipse
```
kompiliert das Projekt minimal ohne css-Generierung und Tests

```
cd src/npm; node_modules/.bin/webpack --watch
```
beobachtet alle JavaScript-Dateien und kompiliert das Bundle bei Änderungen neu.

Für die Entwicklung an den Javascript Dateien der Admin-Platform kann folgender Trick den Workflow erhöhen:

1. Terminal-Fenster öffnen und zu ```bikehh/src/npm``` navigieren
2. den Befehl ```node_modules/.bin/webpack --watch``` laufen lassen
3. dies führt dazu, dass das Projekt nicht jedes mal neu compiliert werden muss, nachdem Änderungen an einer JS-Datei vorgenommen wurden

Über ```localhost:8080/h2``` kann zur Datenbankkonsole navigiert werden. Hier wird sich mit den Credentials aus ```application.properties``` eingelogt. Nun kann mit SQL Anfragen an die Datenbank gestellt werden

```data.sql``` definiert Datenbank-Vorgänge beim Hochfahren des Systems. Dies ist also der Punkt um Tabellen manuell zu löschen / erstellen nach Bedarf. Die Datei beinhaltet eine Reihe von auskommentierten Befehlen, welche alle Tabellen löschen, sollten während der Entwicklung die alten Inhalte nicht zum neuen Schema passen.

## Dokumentation

### Admin-Plattform
Im Folgenden wird die ADFC Admin-Platform vorgestellt und so gut es geht erläutert. Sie soll als Hilfe für das kommende Blockpraktikum, welches dieses System weiterentwickelt, dienen.

Das Projekt ist ein Maven-Projekt, welches am einfachsten mit Eclipse gebaut und entwickelt wird. Zum Bauen des Projekts kann die Datei ```BikeHH - Build.launch``` ausgeführt werden. Das Projekt selbst wird mit ```BikehhApplication.java``` gestartet und kann im Browser auf ```http://localhost:8080/``` angesehen werden.

#### ToDos

Im Code der Admin-Plattform wurden alle uns bekannten Stellen, an denen Funktionalität fehlt oder allgemein noch weiter dran gearbeitet werden muss mit ```// TODO``` markiert.

Mit Tools wie [grep](https://github.com/BurntSushi/ripgrep) und Terminal-Befehlen wie ```rg TODO | grep TODO | wc -l``` kann das gesamte Projekt nach Todos untersucht werden. Eclipse findet zwar auch Todos, jedoch nur in den Java-Dateien und nicht im JS/HTML-Code. 

#### Architektur

In der Entwicklung wurde sich am Werkzeug-Material-Ansatz (WAM-Ansatz) der Universität Hamburg orientiert.

Die HTML-Dateien wurden von den Javascript Dateien getrennt, um diese jeweils kürzer zu machen und die Lesbarkeit zu erhöhen. Die HTML-Dateien sind unter ```src/main/resources/templates/...```, die Javascript Dateien unter ```src/npm/src/bundle/...```

#### Login / Logout
Daten zum Einloggen: (Genaueres ist in ```application.properties``` einsehbar)
Benutzername: ```admin@chef.lol ```
Passwort: ```admin_pw```

Neue User können manuell in der Datei ```data.sql``` in der H2 Datenbank angelegt werden.

Mit einem Klick auf ```Ausloggen``` in der Navigationsleiste kann sich ausgeloggt werden.

#### &Uuml;bersicht
In dieser Ansicht sollen einige statistische Auswertungen über die Umfragen und Meldungen erscheinen. Ergebnisse sollen geteilt und exportiert werden können. Diese Seite gilt es noch auszuarbeiten.

##### ÜbersichtController
Dieser Controller ist der Controller für die Übersichtsseite und muss noch angepasst werden. Momentan ist es der Anlaufpunkt nach erfolgreichem Login. Er gibt bisher nur das HTML-Template für ```Uebersicht``` zurück. Ebenso müssen noch Tests erstellt werden.

#### Umfragen
Auf der Umfragen-Seite wird eine Gesamtansicht der Umfragen als Liste dargestellt. Hier gibt es die Möglichkeit eine Einzelansicht einer Umfrage aufzurufen, als auch die Umfragen zu löschen (Achtung: Umfrage wird hier unwiderruflich gelöscht!). Daneben wird der Status der Umfrage (aktiviert/deaktiviert) angezeigt.

##### UmfragenController
Für die Anzeige der Umfragenliste als auch der Einzelumfrage ist im Backend der ```UmfragenController``` zuständig, der die Umfragen über den ```UmfragenService``` aus der Datenbank bezieht. 

##### UmfragenRestController
Der ```UmfragenRestController``` ist für die Löschung und Deaktivierung- bzw. Aktivierung einer Umfrage zuständig und nutzt dafür den ```UmfragenService```.

Die Filterfunktion auf der Umfragen-Seite ist noch nicht ausgebaut.

##### Materialien
* **Umfrage**: Eine Umfrage besteht aus mindestens einer Frage, ist einer Kategorie zugeordnet, findet an einem Standort statt (Längen- und Breitengrad) und hat somit auch eine Adresse. Eine Umfrage hat ein Start-, End- und Erstelldatum und einen Ersteller (Admin oder Fahrradfahrer). Sie kann vom Admin bearbeitet und deaktiviert- bzw. aktiviert werden. Zusätzlich zu einem Standort kann sie eine Fahrtrichtung aufweisen. Sie kann von Benutzern bestätigt werden und weißt daher einen Schwellenwert auf, wieviele Bestätigungen sie braucht.  

* **Frage**: Eine Frage gehört zu einer Umfrage, hat einen Standort, kann mehrere Antwortmöglichkeiten aufweisen und kann Bedingungen enthalten, nachdem sie nur angezeigt wird, wenn in einer vorherigen Frage eine bestimmte Antwort ausgewählt wurde. Ebenso kann sie eine benutzerdefinierte Antwort (Freitext) erlauben. Die eigentliche Frage wird als Titel der Frage spezifiziert. 

* **Adresse**: Die Adresse einer Umfrage mit Straße, Hausnummer, PLZ, Ort. Die Hausnummer ist nicht immer gegeben, da die verwendete OpenStreetMap API nicht zu jedem Koordinatenpaar eine Hausnummer findet.

* **AntwortMoeglichkeit**: Die Antwortmöglichkeit auf eine Frage.

* **Bedingung**: Die Bedingung, wenn eine Frage zu einer Antwort zugewiesen wird.

* **Kategorie**: Die Kategorie zu der die Umfrage gehört. Diese muss noch stark ausgebaut werden und ist momentan nur als einfacher ```String``` realisiert.

##### UmfrageRepository
Das Repository für die Umfragen.

##### UmfragenService
Der Service stellt Methoden bereit, über die Umfragen aus dem Repository abgefragt und manipuliert werden können. So lassen sich hier alle oder einzelne Umfragen abfragen, als auch Umfragen aus dem Repository löschen.

#### Meldungen
Das Backend für die Meldungen wurde bereits angefangen, muss aber noch weiter entwickelt werden. Die Controller und die Entität Meldung müssen noch implementiert und feingeschliffen werden, diese sind jedoch bereits vorhanden. Tests müssen ebenso noch erstellt werden. Das Frontend muss noch erstellt werden.

##### MeldungenController
Dieser Controller behandelt alle Anfragen auf ```\meldungen``` und enthält bereits eine Methode zum Anzeigen einer Liste aller Meldungen.

##### MeldungenRestController
Der ```MeldungenRestController``` ist die Schnittstelle von mobilen Anwendungen zum Backend, damit eine Meldung an das Backend gesendet werden kann. Dort wird diese gespeichert. Die Schnittstelle ist aus Sicherheitsgründen noch hinter ```\api\``` zu schieben (siehe ```config.SecurityConfig.java```).

##### Materialien
Bei den hier beschrieben Materialien ist noch auf Vollständigkeit zu prüfen. Diese müssen mit hoher Wahrscheinlichkeit weiter angepasst werden.
* **Meldung**: Eine Meldung enthält bisher Koordinaten (Längen- und Breitengrad), eine Umfrage auf die die Meldung antwortet (diese muss jedoch nicht gesetzt werden, wenn die Meldung von einem Radfahrer neu erzeugt wurde), eine Liste an Antworten auf Fragen (leer bei Neuerstellung vom Radfahrer), eine Fahrtrichtung (hier wird der Winkel im Bogenmaß nach Osten (siehe unten) sowie einen Text als ```String``` (dieser ist nur bei einer Neuerstellung vom Radfahrer zu setzen).
* **Antwort**: Eine Antwort ist eine Antwort auf eine Umfrage, die automatisch aus einer Meldung generiert wurde. Sie enthält die Meldung, auf die sie sich bezieht, eine Frage, und die tatsächliche Antwort als ```String```

##### MeldungRepository
Das Repository für die Meldungen.

##### MeldungService
Der Service stellt Methoden bereit, über die Meldungen aus dem Repository abgefragt und manipuliert werden können. So lassen sich hier alle oder einzelne Meldungen abfragen, als auch Meldungen aus dem Repository löschen.

#### Karte
In der Kartenansicht werden alle Umfragen aus der Datenbank angezeigt. Bei Klick auf diese können die Daten angeschaut und in die Einzel-Umfrageansicht gewechselt werden. Es besteht die Möglichkeit durch einen beliebigen Klick auf der Karte direkt an diesem Ort eine Umfrage zu erstellen oder durch den +-Button in der unteren, rechten Ecke eine Umfrage (ohne vorherige Ortsangabe) zu erstellen.

#### Fahrtrichtungen
Jede Umfrage und jede Frage können optional eine Fahrtrichtung besitzen. Diese wird über einen Pfeil symbolisiert und kann mit einem separaten Marker, der die Richtung vorgibt, verändert werden. Fahrtrichtungen werden im Bogenmaß gespeichert, wobei Osten dem Wert 0=2\*PI entspricht. Folglich wäre Norden PI/2, Westen wäre PI und Süden PI\*3/2. Eine Fahrtrichtung von 0.0 ist ungültig und steht als Platzhalter für *keine Fahrtrichtung gesetzt*. Entsprechend ist der Wertebereich *0 < Fahrtrichtung <= PI\*2*.
Diese Funktionalität ist in erster Linie dafür gedacht, um auf eine bestimmte Fahrtrichtung hinzuweisen, aber kann bei Bedarf natürlich für beliebiges verwendet werden (z.B. Frage, bei der es um eine Abzweigung in eine möglichst genaue Richtung geht).

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

##### Wie funktioniert der Code der Seite mit Vue.js?

Für ein dynamisches Formular und das schnelle parsen der Textfelder nach Informationen der Umfrage verwendet ```/umfrage-erstellen``` das JS-Framework ```Vue.js```. Dabei können einzelne HTML-Elemente über ```v-model``` mit einer in ```data``` deklarierten Variable verknüpft werden und gewinnt dadurch Funktionalität. Weitere praktische Funktionen von Vue.js sind ```v-if``` und ```v-for```, mit denen sich if-Bedingungen und for-Schleifen auf HTML-Ebene (Um z.B. für jeden Eintrag eines Arrays eine Überschrift zu erzeugen) umsetzen lassen. Für weitere Informationen siehe [Vue.js-Docs](https://vuejs.org/).

##### Erstellen und Bearbeiten in einem

Weil diese Seite mit Abstand am meisten Funktionalität aufweist und zusätzlich zum Umfragen erstellen auch zum Bearbeiten von Umfragen verwendet wird ist sie die komplexeste. Dabei wird versucht, möglichst viele Methoden der Umfrageerstellung für die Bearbeitung wiederzuverwenden. Die Variable ```editiermodus``` gibt jeweils an, in welchem Modus man sich befindet. Abhängig davon geschehen dann allerlei Fallunterscheidungen. Beispiel: Bei einer neuen Umfrage wird keine Id ans Backend geschickt, diese wird von der Datenbank gesetzt. So wird garantiert, dass es keine weitere Umfrage mit der gleichen Id gibt. Wird jedoch eine Umfrage bearbeitet, existiert die Id schon, sie muss also an das Backend zurückgeschickt werden. Sie wird schließlich benötigt, um den alten Datenbankeintrag zu aktualisieren.

Wird eine Umfrage bearbeitet ist auch die Validierung eine leicht andere. So darf nun zum Beispiel das Startdatum in der Vergangenheit liegen.

Eine neue Umfrage wird per POST, eine bearbeitete per PATCH ans Backend geschickt.

##### UmfragenErstellenController
In diesem Controller kann der Ersteller einer Umfrage abgefragt werden, als auch eine bestimmte Umfrage abgefragt werden, welche bearbeitet werden soll.

##### UmfragenErstellenRestController
Hier wird eine Umfrage gespeichert oder eine bestehende in der Datenbank aktualisiert. Dazu wird sie vorher noch einmal durch den Aufruf von ```Validator.umfrageIstValide(umfrage)``` validiert und anschließend gespeichert.
Wenn die Validierung schief läuft, wird eine ```ResponseStatusException``` mit dem HTTP Status ```NOT_ACCEPTABLE``` geworfen und die Umfrage nicht gespeichert. Das Frontend wird in diesem Fall eine Fehlermeldung anzeigen. 

##### Validator
Die Hilfklasse ```Validator``` validiert eine Umfrage. Hierzu werden als erstes die Daten in Formate gebracht, mit denen sie verglichen werden können. Dann werden die Bedingungen, die auch im Frontend getestet werden, erneut durchgeführt. 

#### Tests

Tests der Admin-Plattform sind wie alle anderen Tests der Nutzerverwaltung unter ```src/test/java/de/wps/bikehh/...``` zu finden und lassen sich bequem mit Eclipse ausführen.

Für die Tests werden mit ```Mockito``` die Datenbank, deren Inhalte und Anweisungen auf diesen simuliert. Dies beschleunigt die Ausführung der Tests und garantiert, dass die Tests nicht vom momentanen Zustand der Datenbank beeinflusst werden.

### Nutzerverwaltung

#### Allgemeines
Die Benutzerverwaltung stellt eine REST-API mit OAuth(vereinfacht) Authentication für die Mobile-Clients zur Verfügung, sowie Services für das ADFC-Frontend. Damit lassen sich User abfragen, erstellen, updaten sowie löschen. Zudem enthält es grundlegende Funktionen wie Account verifizieren oder Passwort zurücksetzen.

#### API-Referenz
Eine API-Referenz findet sich im Repository unter dem Dateinamen ```benutzerverwaltung_api_documentation.htm```

#### Authentifizierung & Autorisierung
##### Mobile-Clients
Die REST-API ist abgesichert mit einem API-Key, welcher bei jeder Anfrage im Header mitgeschickt werden soll. Der Header trägt den Namen ```X-API-Key``` und der Token ist in der ```application.properties``` unter ``bikehh.api.key`` vermerkt.
Ein eingeloggter User sendet im ```Authorization``` Header seinen OAuth AccessToken mit, um ihn zu authentizifieren.

##### Web-Frontend
Spring-Boot erstellt einen Cookie für eine neue Session und dieser wird automatisch übermittelt, sowie serverseitig authentifiziert.

#### Übersicht

##### AuthController
Dieser Controller ist dafür da, um Anfragen entgegenzunehmen, um einen User einzuloggen bzw. auszuloggen. Zudem validiert er die Anfragen.

##### AuthService
Dieser Service kümmert sich darum, die Sessions zu verwalten.

##### SessionRepository
Das Repository für die Sessions.

##### UserController
Dieser Controller nimmt Anfragen entgegen, um einen User zu registrieren, updaten oder zu löschen.

##### UserDetailsService
Dieser Service kümmert sich darum, einen User zu verwalten.

##### UserAuthenticationRepository
Das Repository für die User.

##### UserAdviceController
Dieser Controller ist dafür zuständig, damit Spring-Boot automatisch einen User aus dem Web-Cookie authentifziert.

##### PasswordController
Dieser Controller nimmt Anfragen entgegen, um sein Password zurückzusetzen, wenn man es vergessen hat.

##### PasswordDetailsService
Dieser Service kümmert sich darum, Passwort-Reset Tokens zu verwalten. Das ```Reset``` Model wird hierfür verwendet.

##### PasswordAuthenticationRepository
Das Repository für die Passwort-Reset Tokens.

##### PasswordController
Dieser Controller nimmt Anfragen entgegen, um sein Password zurückzusetzen, wenn man es vergessen hat.

##### VerifyDetailsService
Dieser Service kümmert sich darum, Verifizierung Tokens zu verwalten. Das ```Verify``` Model wird hierfür verwendet.

##### VerificationAuthenticationRepository
Das Repository für die Verifizierung Tokens.

##### SmtpService
Service, um Emails zu verschicken. Funktioniert im local Environment jedoch nicht. Fürs dev und prod Environment muss ein entsprechender Email-Server in der `application.properties` hinterlegt werden.

##### Materialien
* **BikehhUserDetails**: Spring-Boot User Model, wrappt ein ``User`` Objekt. Wird hauptsächlich verwendet, damit Spring-Boot einen User automatisch durch einen Cookie authenfiziert.
* **User**: Stellt einen User im System dar
* **Rollen**: Rollen eines Users
* **Session**: Eine Session wird erstellt, wenn sich ein User einloggt. Dieser enthält eine Referenz auf einen User, sowie einen AccessToken
* **Mail**: Repräsentiert eine Email mit Empfänger, Betreff, sowie Body
* **Reset**: Ein Token, welcher verwendet wird, um sein Passwort zurückzusetzen
* **Verification**: Ein Token, welcher verwendet wird, um seinen Account zu verifizieren

##### Exception
Globales Exception handling des Benutzerverwaltung Pakets. Fehler in Services und Controller werden hier behandelt.

##### Security
OAuth Authentication Filter. Wichtig hierfür ist die Klasse `SecurityConfig` im Config Paket. Dort wird der Filter auf die einzelnen Endpunkte gesetzt bzw. auch andere Regeln definiert.

##### Dto
Hier sind die Data-Transfer Objekte drin, welche fürs deserialisieren/serialisieren der json Objekte zuständig sind. Dies wird von Spring-Boot automatisch gemacht.

##### Util
Dort sind Klassen bzw. Hilfsfunktionen drin.
