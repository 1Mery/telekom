package com.demo.telekom.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accessory_barcodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessoryBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accessory_id", nullable = false)
    private Accessory accessory;

    @Column(nullable = false, unique = true, length = 100)
    private String barcode;
}
