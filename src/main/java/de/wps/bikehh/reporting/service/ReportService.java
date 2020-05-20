package de.wps.bikehh.reporting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.wps.bikehh.reporting.dto.ReportDto;
import de.wps.bikehh.reporting.material.Report;
import de.wps.bikehh.reporting.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	public List<ReportDto> getAllReports() {
		List<ReportDto> result = new ArrayList<>();
		for (Report report : reportRepository.findAll()) {
			result.add(ReportDto.of(report));
		}
		return result;
	}
}
