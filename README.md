# Bike HH - Fahrrad in Hamburg
Im Zuge des Praktikums "Fahrrad in Hamburg" der Universität Hamburg existiert dieses Repository als Vorlage für die Studenten.


## Dokumentation

### Admin-Plattform






























# Struktur
### Backend
* [Spring Boot Framework](https://spring.io/projects/spring-boot)
* Eine lokale H2 Datenbank
    * (Optional) Ein Docker-Container mit einem MariaDB image

### Frontend
* [Angular](https://angular.io)
    * [Angular Material Design](https://material.angular.io/)
* [OpenLayers Kartenframework](https://openlayers.org/)

# Erste Schritte

### Node
Zunächst müssen Node und Npm installiert werden. Derzeitig sind folgende Versionen bei mir installiert:
```
node: v13.2.0
npm: 6.13.1
```

Node und Npm findet ihr [hier](https://nodejs.org/en/download/). Der Download beinhaltet Node und npm. Es wird empfohlen die LTS (Long Term Support) Version zu nehmen.

### Spring Boot
Spring Boot ist in diesem Projekt bereits initialisiert. Solltet ihr jedoch eine neue Spring Boot App initialisieren wollen, nutzt [diese Seite](https://start.spring.io/) zur einfachen Erstellung einer grundlegenden Projektstruktur.  
Bei diesem Download ist eine lokale Version von Maven dabei. Diese *kann* gelöscht werden, falls ihr eine globale Version oder die von der IDE eurer Wahl benutzen wollt.
Hierbei können folgende Dateien/Ordner aus der neuen Ordnerstruktur gelöscht werden:
```diff
- .mvn/
- mvnw
- mvnw.cmd
- HELP.md
```

### Angular
Es wird empfohlen das Angular [command-line interface](https://angular.io/cli) tool zu installieren. Es wird empfohlen das global zu installieren. Hierfür werden Administrationsrechte benötigt.

```
npm install -g @angular/cli
```

### Angular mit Spring Boot
Ein Ausführliches Tutorial findet ihr [hier](https://www.baeldung.com/spring-boot-angular-web). Es lohnt sich wirklich dieses durchzulesen.  
Um eine ordentliche Projektstruktur zu halten, teilen wir das Repository auf Rootebene in Unterordner auf. Einer für das Backend und einer für das Frontend.  
Wir initialisieren unseren Angular-Sourcecode im Webapp Ordner, indem wir auf Rootebene folgenden Befehl ausführen:
```
ng new Webapp
```
Das generiert uns einen neuen Workspace-Ordner namens Webapp mit einem sehr großen Haufen an Dateien. Als zusätzliche Optionen beim Ausführen des Befehls haben wir uns dafür entschieden, Routing zu inkludieren und CSS zu benutzen.

### OpenLayers und Angular
Zum [Einbinden von OpenLayers](https://openlayers.org/en/latest/doc/tutorials/bundle.html) haben wir folgendes ausgeführt:
```
npm i ol
```
Das OpenLayers package heißt kurz ``ol``. Mit dem Befehl installieren wir es als "Laufzeit-Dependency". Javascript Bibliotheken, die nur zur Entwicklung und nicht zur Produktionslaufzeit gebraucht werden werden mit dem Schema ``npm i --save-dev <package name>`` installiert.  
Dies kommt auch direkt zum Einsatz, da wir folgendes ausführen müssen, um die OpenLayers Typen in Typescript benutzen zu können:
```
npm i --save-dev @types/ol
```
