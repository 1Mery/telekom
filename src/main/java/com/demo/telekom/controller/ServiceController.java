package com.demo.telekom.controller;

import com.demo.telekom.dto.CreateServiceRequestDto;
import com.demo.telekom.dto.ServiceSaleRequestDto;
import com.demo.telekom.entity.DailyTransaction;
import com.demo.telekom.entity.ServiceEntity;
import com.demo.telekom.service.ServiceManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceManagementService serviceManagementService;

    @PostMapping("/create")
    public ResponseEntity<ServiceEntity> createService(@Valid @RequestBody CreateServiceRequestDto request) {
        return ResponseEntity.ok(
                serviceManagementService.createService(request.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getActiveServices() {
        return ResponseEntity.ok(serviceManagementService.getAllActiveServices());
    }

    @PostMapping("/sell")
    public ResponseEntity<DailyTransaction> sellService(@Valid @RequestBody ServiceSaleRequestDto request) {
        return ResponseEntity.ok(
                serviceManagementService.processServiceSale(request.getServiceId(), request.getQuantity())
        );
    }
}