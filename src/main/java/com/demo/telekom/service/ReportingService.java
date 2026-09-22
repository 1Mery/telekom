package com.demo.telekom.service;

import com.demo.telekom.dto.ZReportResponseDto;
import com.demo.telekom.entity.DailyTransaction;
import com.demo.telekom.entity.ZReport;
import com.demo.telekom.repository.DailyTransactionRepository;
import com.demo.telekom.repository.ZReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final DailyTransactionRepository transactionRepository;
    private final ZReportRepository zReportRepository;

    // 1. GÜN SONU ALMA VE RAPORU KAYDETME
    @Transactional
    public ZReportResponseDto closeDayAndGenerateReport() {
        List<DailyTransaction> openTransactions = transactionRepository.findByStatus("OPEN");

        if (openTransactions.isEmpty()) {
            throw new RuntimeException("Kapatılacak açık bir işlem bulunamadı!");
        }

        int totalServicesCount = 0;
        BigDecimal totalAccessoryRevenue = BigDecimal.ZERO;

        for (DailyTransaction tx : openTransactions) {
            if ("SERVICE".equalsIgnoreCase(tx.getTransactionType())) {
                totalServicesCount += tx.getQuantity();
            } else if ("ACCESSORY".equalsIgnoreCase(tx.getTransactionType()) || "PRODUCT".equalsIgnoreCase(tx.getTransactionType())) {
                totalAccessoryRevenue = totalAccessoryRevenue.add(tx.getTotalAmount());
            }
            tx.setStatus("CLOSED"); // Gün kapandı
        }

        // Açık işlemleri kapatıldı olarak veritabanına kaydet
        transactionRepository.saveAll(openTransactions);

        // Z-Raporunu Oluştur ve Kaydet
        ZReport zReport = ZReport.builder()
                .reportDate(LocalDate.now())
                .totalServiceCount(totalServicesCount)
                .totalAccessoryRevenue(totalAccessoryRevenue)
                .netProfit(totalAccessoryRevenue) // İleride gider kalemi eklendiğinde net kâr güncellenir
                .build();

        zReportRepository.save(zReport);

        return ZReportResponseDto.builder()
                .reportDate(zReport.getReportDate())
                .totalServiceCount(totalServicesCount)
                .totalAccessoryRevenue(totalAccessoryRevenue)
                .totalExpense(BigDecimal.ZERO)
                .netProfit(zReport.getNetProfit())
                .build();
    }

    // 2. BELİRLİ BİR TARİHİN Z-RAPORUNU GETİRME (Geçmiş Gün Sorgulama)
    @Transactional(readOnly = true)
    public ZReportResponseDto getReportByDate(LocalDate date) {
        ZReport zReport = zReportRepository.findByReportDate(date)
                .orElseThrow(() -> new RuntimeException("Belirtilen tarihe ait gün sonu raporu bulunamadı: " + date));

        return ZReportResponseDto.builder()
                .reportDate(zReport.getReportDate())
                .totalServiceCount(zReport.getTotalServiceCount())
                .totalAccessoryRevenue(zReport.getTotalAccessoryRevenue())
                .totalExpense(zReport.getTotalExpense())
                .netProfit(zReport.getNetProfit())
                .build();
    }
}