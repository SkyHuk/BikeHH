package de.wps.bikehh.reporting.dto;

import de.wps.bikehh.reporting.material.Report;

public class ReportDto {
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static ReportDto of(Report report) {
		ReportDto result = new ReportDto();
		result.setDescription(report.getDescription());
		return result;
	}
}
