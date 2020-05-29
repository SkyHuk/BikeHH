package de.wps.bikehh.adfc.controller;

import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

// import de.wps.bikehh.benutzerverwaltung.material.User;
// import de.wps.bikehh.willkommen.material.Bier;

@Controller
@RequestMapping("reportList")
public class ReportListController {
	public String showReportList() {
		return "adfc/reportList";
	}
}
