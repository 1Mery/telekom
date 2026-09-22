package com.demo.telekom.repository;

import com.demo.telekom.entity.ProductBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductBarcodeRepository extends JpaRepository<ProductBarcode, Long> {
    Optional<ProductBarcode> findByBarcode(String barcode);
}
