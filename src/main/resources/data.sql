DROP TABLE IF EXISTS `Benutzer` cascade;

CREATE TABLE `Benutzer` (
	`id` LONG AUTO_INCREMENT  PRIMARY KEY,
	`email_adresse` VARCHAR(250) NOT NULL,
    `verschluesseltes_passwort` VARCHAR(250) NOT NULL,
	`rolle` VARCHAR(250) NOT NULL,
	`ist_gesperrt` boolean DEFAULT false
);

INSERT INTO `Benutzer` (`email_adresse`, `verschluesseltes_passwort`, `rolle`) VALUES
('admin@chef.lol', 'sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=', 'administrator');

-- simple script to drop all tables and by this delete everything
/*DROP TABLE IF EXISTS `Antwort_Moeglichkeit` cascade;
DROP TABLE IF EXISTS `Bedingung` cascade;
DROP TABLE IF EXISTS `Frage` cascade;
DROP TABLE IF EXISTS `antwort` cascade;
DROP TABLE IF EXISTS `Frage_antworten` cascade;
DROP TABLE IF EXISTS `frage_bedingungen` cascade;
DROP TABLE IF EXISTS `meldung` cascade;
DROP TABLE IF EXISTS `MELDUNG_ANTWORTEN_AUF_FRAGEN` cascade;
DROP TABLE IF EXISTS `Umfrage` cascade;
DROP TABLE IF EXISTS `Umfrage_fragen` cascade;
DROP TABLE IF EXISTS `adresse` cascade;
DROP TABLE IF EXISTS `UMFRAGE_BESTAETIGT_VON_BENUTZERN` cascade;*/