package de.wps.bikehh.meldungen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import de.wps.bikehh.meldungen.controller.MeldungenController;

public class MeldungenListeControllerTest {

	private Model model;
	private MeldungenController meldungenController;

	public MeldungenListeControllerTest() {
		this.meldungenController = new MeldungenController();

		model = mock(Model.class);
	}

	@Test
	public void zeigeMeldungenListeTest() {
		// TODO implement test
		assertEquals("meldungen/meldungen_liste", meldungenController.zeigeMeldungenListe(model));
	}
}
