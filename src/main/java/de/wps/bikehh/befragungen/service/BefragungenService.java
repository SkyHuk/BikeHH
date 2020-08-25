package de.wps.bikehh.befragungen.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.material.Frage;
import de.wps.bikehh.befragungen.repository.BefragungenRepository;
import de.wps.bikehh.befragungen.repository.FragenRepository;
import de.wps.bikehh.benutzerverwaltung.material.User;
import de.wps.bikehh.framework.Contract;
import de.wps.bikehh.meldungen.material.Meldung;

@Service
public class BefragungenService {

	private BefragungenRepository befragungenRepository;
	private FragenRepository fragenRepository;

	@Autowired
	public BefragungenService(BefragungenRepository repository,
			FragenRepository fragenRepository) {
		this.befragungenRepository = repository;
		this.fragenRepository = fragenRepository;
	}

	public Befragung save(Befragung befragung) {
		Contract.notNull(befragung, "befragung");

		return befragungenRepository.save(befragung);
	}

	public boolean doesBefragungExist(long befragungId) {
		return befragungenRepository.existsById(befragungId);
	}

	public boolean doesFrageExist(long fragenId) {
		return fragenRepository.existsById(fragenId);
	}

	public Befragung getBefragung(long id) {
		Contract.check(doesBefragungExist(id), "doesBefragungExist(id)");

		return befragungenRepository.findById(id).get();
	}

	public Frage getFrage(long id) {
		Contract.check(doesFrageExist(id), "doesFrageExist(id)");

		return fragenRepository.findById(id).get();
	}

	public Befragung createNewBefragungFromMeldung(User user, Meldung meldung) {
		Befragung befragung = new Befragung();

		befragung.setLaengengrad(meldung.getLaengengrad());
		befragung.setBreitengrad(meldung.getBreitengrad());
		befragung.setErsteller(user);
		befragung.setMeldung(meldung);

		// TODO: Mit Georg klären wie lang Befragungen zu Meldungen sichtbar
		// sein sollen.
		befragung.setStartDatum(LocalDate.now());
		befragung.setEndDatum(LocalDate.now().plusDays(1));

		befragung.setBestaetigungsSchwellenwert(10);

		Frage frage = new Frage();
		frage.setBefragung(befragung);
		frage.setText("Ist an diesem Ort " + meldung.getKategorie() + " vorzufinden?");
		frage.setAntworten(Arrays.asList("Ja", "Nein"));
		befragung.setFragen(Arrays.asList(frage));

		return befragungenRepository.save(befragung);
	}

	public List<Befragung> getAlleBefragungen() {
		List<Befragung> befragungen = new ArrayList<>();
		befragungenRepository.findAll().forEach(befragungen::add);
		return befragungen;
	}

	public List<Befragung> getAktuelleBefragungen(LocalDate currentDate) {
		List<Befragung> befragungen = new ArrayList<>();
		befragungenRepository.findAll().forEach(befragung -> {
			// Ist die Befragung deaktiviert, wollen wir sie nicht schicken.
			if (befragung.isDisabled()) {
				// return hier ist wie ein continue in einer normalen
				// for-Schleife.
				return;
			}
			// TODO: Nur hinzufügen, wenn die Befragung noch nicht beantwortet
			// wurde. Falls Mehrfachantwort möglich ist, dann checken ob die
			// letzte Antwort 24h her ist.

			// Liegt der Zeitraum der Befragung aus der Datenbank über dem
			// gewählten Datum und sie ist aktiv, wollen wir sie schicken.
			LocalDate startDatum = befragung.getStartDatum();
			LocalDate endDatum = befragung.getEndDatum();
			if ((startDatum.isBefore(currentDate) || startDatum.isEqual(currentDate)) &&
					(endDatum.isAfter(currentDate) || endDatum.isEqual(currentDate))) {
				befragungen.add(befragung);
			}
		});
		return befragungen;
	}

}
