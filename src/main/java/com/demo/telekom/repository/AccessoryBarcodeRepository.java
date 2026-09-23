package com.demo.telekom.repository;

import com.demo.telekom.entity.AccessoryBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccessoryBarcodeRepository extends JpaRepository<AccessoryBarcode, Long> {
    Optional<AccessoryBarcode> findByBarcode(String barcode);
}
