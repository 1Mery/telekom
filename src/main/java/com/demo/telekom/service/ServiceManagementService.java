package com.demo.telekom.service;

import com.demo.telekom.entity.DailyTransaction;
import com.demo.telekom.entity.ServiceEntity;
import com.demo.telekom.repository.DailyTransactionRepository;
import com.demo.telekom.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;
    private final DailyTransactionRepository transactionRepository;

    // Ana ekranda gösterilecek aktif hizmet listesi
    @Transactional(readOnly = true)
    public List<ServiceEntity> getAllActiveServices() {
        return serviceRepository.findByActiveTrue();
    }

    @Transactional
    public ServiceEntity createService(String name) {
        ServiceEntity newService = ServiceEntity.builder()
                .name(name)
                .active(true)
                .build();

        return serviceRepository.save(newService);
    }

    // Ekrandan tıklandığında hizmet satışını gerçekleştiren ve kasaya işleyen metot
    @Transactional
    public DailyTransaction processServiceSale(Long serviceId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            quantity = 1;
        }

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Seçilen hizmet bulunamadı!"));

        BigDecimal totalAmount = service.getPrice().multiply(BigDecimal.valueOf(quantity));

        DailyTransaction transaction = DailyTransaction.builder()
                .transactionType("SERVICE")
                .service(service)
                .quantity(quantity)
                .unitPrice(service.getPrice())
                .totalAmount(totalAmount)
                .status("OPEN")
                .build();

        return transactionRepository.save(transaction);
    }
}