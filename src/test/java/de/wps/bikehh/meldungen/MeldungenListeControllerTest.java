package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.wps.bikehh.meldungen.controller.MeldungenListeController;

public class MeldungenListeControllerTest {

	private MeldungenListeController meldungenListeController;

	public MeldungenListeControllerTest() {
		this.meldungenListeController = new MeldungenListeController();
	}

	@Test
	public void zeigeMeldungenListeTest() {
		// TODO implement test
		assertEquals("adfc/meldungen_liste", meldungenListeController.zeigeMeldungenListe());
	}
}