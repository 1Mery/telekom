package com.demo.telekom.repository;

import com.demo.telekom.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByServiceType(String serviceType);
    List<ServiceEntity> findByActiveTrue();
}
