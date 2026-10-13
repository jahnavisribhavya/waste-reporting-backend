
package com.wastewatch.waste_reporting_backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteReportRepository
        extends JpaRepository<WasteReport, Long> {
}

