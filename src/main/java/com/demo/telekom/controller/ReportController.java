package com.demo.telekom.controller;

import com.demo.telekom.dto.ZReportResponseDto;
import com.demo.telekom.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportingService reportingService;

    @PostMapping("/close-day")
    public ResponseEntity<ZReportResponseDto> closeDay() {
        return ResponseEntity.ok(reportingService.closeDayAndGenerateReport());
    }

    @GetMapping
    public ResponseEntity<ZReportResponseDto> getReportByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportingService.getReportByDate(date));
    }
}