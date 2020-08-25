package de.wps.bikehh.framework;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.wps.bikehh.benutzerverwaltung.material.Rollen;
import de.wps.bikehh.benutzerverwaltung.service.UserService;
import de.wps.bikehh.kategorien.material.Kategorie;
import de.wps.bikehh.kategorien.service.KategorienService;

@Component
public class StartupBean implements InitializingBean {

	@Autowired
	private KategorienService kategorienService;

	@Autowired
	private UserService userService;

	@Override
	public void afterPropertiesSet() throws Exception {
		addUsers();
		addKategorien();
	}

	private void addUsers() {
		if (!userService.isEmpty()) {
			return;
		}

		userService.createUser("admin@bikehh.de", "admin_pw", Rollen.ROLE_ADMIN);
		userService.createUser("user@bikehh.de", "user_pw", Rollen.ROLE_USER);
	}

	private void addKategorien() {
		if (!kategorienService.isEmpty()) {
			return;
		}

		Kategorie frost = kategorienService.addKategorie("Frost");
		kategorienService.addKategorie("leichter Frost", frost.getId());
		kategorienService.addKategorie("mäßiger Frost", frost.getId());
		kategorienService.addKategorie("starker Frost", frost.getId());
		kategorienService.addKategorie("bofrost", frost.getId());

		Kategorie fahrbahnschaden = kategorienService.addKategorie("Fahrbahnschaden");
		kategorienService.addKategorie("Schlagloch", fahrbahnschaden.getId());
		kategorienService.addKategorie("Bodenriss", fahrbahnschaden.getId());
		kategorienService.addKategorie("Fehlende Fahrbahnmarkierung", fahrbahnschaden.getId());

		Kategorie gegenstand = kategorienService.addKategorie("Gegenstand");
		kategorienService.addKategorie("Fahrzeug", gegenstand.getId());
		kategorienService.addKategorie("Umgefallener Baum", gegenstand.getId());
		kategorienService.addKategorie("Lose Steine", gegenstand.getId());

		kategorienService.addKategorie("Sonstiges");
	}

}
