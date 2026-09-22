package com.demo.telekom.repository;

import com.demo.telekom.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    Optional<Accessory> findByCode(String code);
    List<Accessory> findByActiveTrue();
}