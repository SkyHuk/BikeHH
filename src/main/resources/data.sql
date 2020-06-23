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