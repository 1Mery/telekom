package com.demo.telekom.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_accessories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAccessory {

    @EmbeddedId
    private ProductAccessoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accessoryId")
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;
}