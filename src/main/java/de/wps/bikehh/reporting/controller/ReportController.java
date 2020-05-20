package de.wps.bikehh.reporting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.wps.bikehh.reporting.dto.ReportDto;
import de.wps.bikehh.reporting.service.ReportService;

@RestController
@RequestMapping("reports")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping(value = "/all", produces = "application/json")
	public @ResponseBody List<ReportDto> getAllReports() {
		return reportService.getAllReports();
	}

}
