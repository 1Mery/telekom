package com.demo.telekom.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "z_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date", nullable = false, unique = true)
    private LocalDate reportDate;

    @Column(name = "total_service_count")
    private Integer totalServiceCount;

    @Column(name = "total_accessory_revenue")
    private BigDecimal totalAccessoryRevenue;

    @Column(name = "total_expense")
    private BigDecimal totalExpense;

    @Column(name = "net_profit")
    private BigDecimal netProfit;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @PrePersist
    protected void onCreate() {
        this.closedAt = LocalDateTime.now();
    }
}
