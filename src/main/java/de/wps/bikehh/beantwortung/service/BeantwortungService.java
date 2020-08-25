package de.wps.bikehh.beantwortung.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.wps.bikehh.beantwortung.material.Beantwortung;
import de.wps.bikehh.beantwortung.repository.BeantwortungRepository;
import de.wps.bikehh.befragungen.material.Frage;

@Service
public class BeantwortungService {

	private BeantwortungRepository repository;

	public BeantwortungService(BeantwortungRepository repository) {
		this.repository = repository;
	}

	public List<Beantwortung> getAntwortenAufFrage(Frage frage) {
		return repository.findAllByFrage(frage);
	}

	public long getAnzahlAntwortenAufFrage(Frage frage) {
		return repository.findAllByFrage(frage).size();
	}

	public void beantworteFrage(Frage frage, int antwortIndex, String antwortFreitext) {
		Beantwortung beantwortung = new Beantwortung();
		beantwortung.setFrage(frage);
		beantwortung.setAntwortIndex(antwortIndex);
		beantwortung.setAntwortFreitext(antwortFreitext);
		repository.save(beantwortung);
	}

}
