package com.demo.telekom.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ZReportResponseDto {
    private LocalDate reportDate;
    private Integer totalServiceCount;
    private BigDecimal totalAccessoryRevenue;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;
}