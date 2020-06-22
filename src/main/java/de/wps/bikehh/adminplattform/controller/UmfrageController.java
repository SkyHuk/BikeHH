package de.wps.bikehh.adminplattform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.wps.bikehh.adminplattform.material.UmfrageDB;
import de.wps.bikehh.adminplattform.service.UmfragenService;

public class UmfrageController {

	@Autowired
	UmfragenService umfragenService;

	@GetMapping("/umfragen")
	private List<UmfrageDB> getAlleUmfragen() {
		return umfragenService.getAlleUmfragen();
	}

	@GetMapping("/umfragen/{id}")
	private UmfrageDB getUmfrage(@PathVariable("id") int id) {
		return umfragenService.getUmfrageNachId(id);
	}

	@DeleteMapping("/umfragen/{id}")
	private void loescheUmfrage(@PathVariable("id") int id) {
		umfragenService.loesche(id);
	}

	@PostMapping("/umfragen")
	private int speichereUmfrage(@RequestBody UmfrageDB umfrage) {
		umfragenService.speichereOderUpdateUmfrage(umfrage);
		return umfrage.getId();
	}
}
