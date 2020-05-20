package de.wps.bikehh.reporting.repository;

import org.springframework.data.repository.CrudRepository;

import de.wps.bikehh.reporting.material.Report;

public interface ReportRepository extends CrudRepository<Report, Long> {

}
