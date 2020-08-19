package de.wps.bikehh.befragungen.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.befragungen.material.Befragung;
import de.wps.bikehh.befragungen.repository.BefragungenRepository;

@Service
public class BefragungenService {

	private BefragungenRepository befragungenRepository;

	@Autowired
	public BefragungenService(BefragungenRepository repository) {
		this.befragungenRepository = repository;
	}

	public Befragung getBefragung(long id) {
		return befragungenRepository.findById(id).get();
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
