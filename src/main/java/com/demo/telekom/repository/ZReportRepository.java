package com.demo.telekom.repository;

import com.demo.telekom.entity.ZReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ZReportRepository extends JpaRepository<ZReport, Long> {
    Optional<ZReport> findByReportDate(LocalDate reportDate);
}
