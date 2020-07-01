package de.wps.bikehh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.wps.bikehh.uebersicht.controller.UebersichtController;

public class UebersichtControllerTest {

	private UebersichtController uebersichtController;

	public UebersichtControllerTest() {
		this.uebersichtController = new UebersichtController();
	}

	@Test
	public void homeTest() {
		assertEquals("redirect:/uebersicht", uebersichtController.home());
	}

	@Test
	public void zeigeUebersichtTest() {
		assertEquals("adfc/uebersicht", uebersichtController.zeigeUebersicht());
	}
}
