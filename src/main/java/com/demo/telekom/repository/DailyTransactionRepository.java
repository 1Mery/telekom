package com.demo.telekom.repository;

import com.demo.telekom.entity.DailyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Long> {
    List<DailyTransaction> findByStatus(String status); // Örn: 'OPEN' olanları getirir
}
