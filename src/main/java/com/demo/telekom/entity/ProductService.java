package com.demo.telekom.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductService {

    @EmbeddedId
    private ProductServiceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
}