package de.wps.bikehh.meldungen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungenRepository;
import de.wps.bikehh.meldungen.service.MeldungenService;

public class MeldungServiceTest {
	private MeldungenRepository meldungRepository;
	private MeldungenService meldungService;
	private Meldung meldung;

	public MeldungServiceTest() {
		// mocke Repository
		this.meldungRepository = mock(MeldungenRepository.class);

		// mocke Service
		this.meldungService = new MeldungenService(meldungRepository);

		// mocke Meldung
		meldung = mock(Meldung.class);
	}

	@Test
	public void getAlleMeldungenTest() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungen.add(meldung);

		when(meldungRepository.findAll()).thenReturn(meldungen);

		meldungService.getAlleMeldungen();
		Mockito.verify(meldungRepository, times(1)).findAll();

		assertEquals(meldungen, meldungService.getAlleMeldungen());
	}

	@Test
	public void getMeldungNachIdTest() {
		when(meldungRepository.findById((long) meldung.getId())).thenReturn(Optional.of(meldung));

		meldungService.getMeldungNachId(meldung.getId());
		Mockito.verify(meldungRepository, times(1)).findById((long) meldung.getId());

		assertEquals(meldung, meldungService.getMeldungNachId(meldung.getId()));
	}

	@Test
	public void speichereOderUpdateMeldungTest() {
		meldungService.speichereOderUpdateMeldung(meldung);
		Mockito.verify(meldungRepository, times(1)).save(meldung);
	}

	@Test
	public void loescheTest() {
		meldungService.loesche(meldung.getId());
		Mockito.verify(meldungRepository, times(1)).deleteById((long) meldung.getId());
	}
}
