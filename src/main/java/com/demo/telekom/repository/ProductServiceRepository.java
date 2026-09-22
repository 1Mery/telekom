package com.demo.telekom.repository;

import com.demo.telekom.entity.ProductService;
import com.demo.telekom.entity.ProductServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductServiceRepository extends JpaRepository<ProductService, ProductServiceId> {
    List<ProductService> findByProductId(Long productId);
}
