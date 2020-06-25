package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.wps.bikehh.meldungen.material.Meldung;
import de.wps.bikehh.meldungen.repository.MeldungRepository;
import de.wps.bikehh.meldungen.service.MeldungService;

public class MeldungServiceTest {
	private MeldungRepository meldungRepository;
	private MeldungService meldungService;
	private Meldung meldung;

	public MeldungServiceTest() {
		// mocke Repository
		this.meldungRepository = mock(MeldungRepository.class);

		// mocke Service
		this.meldungService = new MeldungService(meldungRepository);

		// mocke Meldung
		meldung = mock(Meldung.class);
	}

	@Test
	public void getAlleMeldungenTest() {
		List<Meldung> meldungen = new ArrayList<Meldung>();
		meldungen.add(meldung);

		when(meldungRepository.findAll()).thenReturn(meldungen);

		assertEquals(meldungen, meldungService.getAlleMeldungen());
	}

	@Test
	public void getMeldungNachIdTest() {
		when(meldungRepository.findById((long) meldung.getId())).thenReturn(Optional.of(meldung));

		assertEquals(meldung, meldungService.getMeldungNachId(meldung.getId()));
	}

	@Test
	public void speichereOderUpdateMeldungTest() {
		// TODO Test ausfüllen
		meldungService.speichereOderUpdateMeldung(meldung);
	}

	@Test
	public void loescheTest() {
		// TODO Test ausfüllen
		meldungService.loesche(meldung.getId());
	}
}
