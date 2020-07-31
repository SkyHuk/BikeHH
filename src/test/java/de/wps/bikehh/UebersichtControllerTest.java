package de.wps.bikehh;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
		assertEquals("uebersicht/uebersicht", uebersichtController.zeigeUebersicht());
	}
}
