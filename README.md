# Bike HH - Fahrrad in Hamburg
Im Zuge des Praktikums "Fahrrad in Hamburg" der Universität Hamburg existiert dieses Repository als Vorlage für die Studenten.

# Struktur
### Backend
* [Spring Boot Framework](https://spring.io/projects/spring-boot)
* Eine lokale H2 Datenbank
    * (Optional) Ein Docker-Container mit einem MariaDB image

### Frontend
* [Angular](https://angular.io)
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

