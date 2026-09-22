package com.demo.telekom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateAccessoryRequestDto {

    @NotBlank(message = "Ürün adı boş olamaz")
    private String name;

    private String code;

    @NotNull(message = "Fiyat boş olamaz")
    @Positive(message = "Fiyat 0'dan büyük olmalıdır")
    private BigDecimal price;

    private Integer initialStock = 0;
    private String barcode;
}