
package com.wastewatch.waste_reporting_backend;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class WasteReportController {

    private final WasteReportRepository repository;

    public WasteReportController(WasteReportRepository repository) {
        this.repository = repository;
    }

    // READ: Get all waste reports
    @GetMapping
    public List<WasteReport> getAllReports() {
        return repository.findAll();
    }

    // CREATE: Save a new waste report
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WasteReport createReport(@RequestBody WasteReport report) {
        report.setId(null);
        return repository.save(report);
    }

    // READ: Get one waste report
    @GetMapping("/{id}")
    public WasteReport getReportById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Report not found"));
    }

    // UPDATE: Update an existing waste report
    @PutMapping("/{id}")
    public WasteReport updateReport(
            @PathVariable Long id,
            @RequestBody WasteReport updatedReport) {

        WasteReport report = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Report not found"));

        report.setTitle(updatedReport.getTitle());
        report.setCategory(updatedReport.getCategory());
        report.setDescription(updatedReport.getDescription());
        report.setLocation(updatedReport.getLocation());
        report.setLatitude(updatedReport.getLatitude());
        report.setLongitude(updatedReport.getLongitude());
        report.setStatus(updatedReport.getStatus());

        return repository.save(report);
    }

    // DELETE: Delete a waste report
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Report not found");
        }

        repository.deleteById(id);
    }
}
