package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("meldungen")
public class ReportListController {

	@GetMapping
	public String showReportList() {

		return "adfc/report_list";
	}
}
