package com.demo.telekom.repository;

import com.demo.telekom.entity.ProductAccessory;
import com.demo.telekom.entity.ProductAccessoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductAccessoryRepository extends JpaRepository<ProductAccessory, ProductAccessoryId> {
    List<ProductAccessory> findByProductId(Long productId);
}
