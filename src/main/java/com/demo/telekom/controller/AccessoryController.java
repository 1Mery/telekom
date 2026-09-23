package com.demo.telekom.controller;

import com.demo.telekom.dto.AccessorySaleRequestDto;
import com.demo.telekom.dto.CreateAccessoryRequestDto;
import com.demo.telekom.entity.Accessory;
import com.demo.telekom.entity.DailyTransaction;
import com.demo.telekom.entity.AccessoryBarcode;
import com.demo.telekom.service.AccessoryManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryManagementService accessoryManagementService;

    @GetMapping
    public ResponseEntity<List<Accessory>> getActiveAccessories() {
        return ResponseEntity.ok(accessoryManagementService.getAllActiveAccessories());
    }

    @PostMapping("/sell")
    public ResponseEntity<DailyTransaction> sellAccessory(@Valid @RequestBody AccessorySaleRequestDto request) {
        return ResponseEntity.ok(
                accessoryManagementService.sellAccessory(
                        request.getBarcode(),
                        request.getAccessoryId(),
                        request.getQuantity()
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Accessory> createAccessory(@Valid @RequestBody CreateAccessoryRequestDto request) {
        return ResponseEntity.ok(
                accessoryManagementService.createNewAccessory(
                        request.getName(),
                        request.getCode(),
                        request.getPrice(),
                        request.getInitialStock(),
                        request.getBarcode()
                )
        );
    }

    @PostMapping("/add-stock")
    public ResponseEntity<Accessory> addStock(
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) Long accessoryId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(
                accessoryManagementService.addStock(barcode, accessoryId, quantity)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> updateAccessory(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) BigDecimal price) {
        return ResponseEntity.ok(
                accessoryManagementService.updateAccessory(id, name, code, price)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAccessory(@PathVariable Long id) {
        accessoryManagementService.deactivateAccessory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-barcode/{barcode}")
    public ResponseEntity<Void> deactivateAccessoryByBarcode(@PathVariable String barcode) {
        accessoryManagementService.deactivateAccessoryByBarcode(barcode);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accessoryId}/add-barcode")
    public ResponseEntity<AccessoryBarcode> addBarcodeToAccessory(
            @PathVariable Long accessoryId,
            @RequestParam String barcode) {
        return ResponseEntity.ok(
                accessoryManagementService.addBarcodeToAccessory(accessoryId, barcode)
        );
    }

    @DeleteMapping("/barcode/{barcode}")
    public ResponseEntity<Void> removeBarcode(@PathVariable String barcode) {
        accessoryManagementService.removeBarcode(barcode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/barcode/update")
    public ResponseEntity<AccessoryBarcode> updateBarcode(
            @RequestParam String oldBarcode,
            @RequestParam String newBarcode) {
        return ResponseEntity.ok(
                accessoryManagementService.updateBarcode(oldBarcode, newBarcode)
        );
    }
}