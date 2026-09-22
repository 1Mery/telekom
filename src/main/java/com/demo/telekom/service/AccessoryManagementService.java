package com.demo.telekom.service;

import com.demo.telekom.entity.Accessory;
import com.demo.telekom.entity.DailyTransaction;
import com.demo.telekom.entity.ProductBarcode;
import com.demo.telekom.repository.AccessoryRepository;
import com.demo.telekom.repository.DailyTransactionRepository;
import com.demo.telekom.repository.ProductBarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoryManagementService {

    private final AccessoryRepository accessoryRepository;
    private final ProductBarcodeRepository barcodeRepository;
    private final DailyTransactionRepository transactionRepository;

    // Aktif tüm aksesuarları listeleme (Ekran için)
    @Transactional(readOnly = true)
    public List<Accessory> getAllActiveAccessories() {
        return accessoryRepository.findByActiveTrue();
    }

    // 1. SATIŞ YAP (Barkodlu veya Manuel)
    @Transactional
    public DailyTransaction sellAccessory(String barcode, Long accessoryId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            quantity = 1;
        }

        Accessory accessory = findAccessoryByBarcodeOrId(barcode, accessoryId);

        if (accessory.getStockQuantity() < quantity) {
            throw new RuntimeException("Yetersiz stok! Mevcut stok: " + accessory.getStockQuantity());
        }

        // Stok Düş
        accessory.setStockQuantity(accessory.getStockQuantity() - quantity);
        accessoryRepository.save(accessory);

        BigDecimal totalAmount = accessory.getPrice().multiply(BigDecimal.valueOf(quantity));

        // Kasaya İşlem Kaydı
        DailyTransaction transaction = DailyTransaction.builder()
                .transactionType("ACCESSORY")
                .accessory(accessory)
                .quantity(quantity)
                .unitPrice(accessory.getPrice())
                .totalAmount(totalAmount)
                .status("OPEN")
                .build();

        return transactionRepository.save(transaction);
    }

    // 2. YENİ AKSESUAR KATEGORİSİ / ÜRÜNÜ TANIMLAMA
    @Transactional
    public Accessory createNewAccessory(String name, String code, BigDecimal price, Integer initialStock, String barcode) {
        Accessory accessory = Accessory.builder()
                .name(name)
                .code(code)
                .price(price)
                .stockQuantity(initialStock != null ? initialStock : 0)
                .active(true)
                .build();

        Accessory savedAccessory = accessoryRepository.save(accessory);

        // Barkod girildiyse/okutulduysa ilişkilendir
        if (barcode != null && !barcode.isBlank()) {
            ProductBarcode barcodeEntity = ProductBarcode.builder()
                    .barcode(barcode)
                    .build();
            barcodeRepository.save(barcodeEntity);
        }

        return savedAccessory;
    }

    // 3. MEVCUT ÜRÜNE STOK EKLEME (Barkodlu veya Manuel Mal Kabul)
    @Transactional
    public Accessory addStock(String barcode, Long accessoryId, Integer addedQuantity) {
        if (addedQuantity == null || addedQuantity <= 0) {
            throw new RuntimeException("Eklenecek stok miktarı pozitif olmalıdır!");
        }

        Accessory accessory = findAccessoryByBarcodeOrId(barcode, accessoryId);
        accessory.setStockQuantity(accessory.getStockQuantity() + addedQuantity);

        return accessoryRepository.save(accessory);
    }

    // Yardımcı Metot: Barkod veya ID ile Aksesuar Bulma
    private Accessory findAccessoryByBarcodeOrId(String barcode, Long accessoryId) {
        if (barcode != null && !barcode.isBlank()) {
            ProductBarcode barcodeObj = barcodeRepository.findByBarcode(barcode)
                    .orElseThrow(() -> new RuntimeException("Sistemde bu barkoda ait ürün/aksesuar bulunamadı: " + barcode));

            if (barcodeObj.getAccessory() == null) {
                throw new RuntimeException("Bu barkod bir aksesuara ait değil!");
            }
            return barcodeObj.getAccessory();
        } else if (accessoryId != null) {
            return accessoryRepository.findById(accessoryId)
                    .orElseThrow(() -> new RuntimeException("Seçilen aksesuar bulunamadı!"));
        } else {
            throw new RuntimeException("Lütfen bir barkod okutun veya listeden ürün seçin!");
        }
    }
}