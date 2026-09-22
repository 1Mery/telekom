package com.demo.telekom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestDto {

    @NotNull(message = "İşlem tipi boş olamaz (ACCESSORY, SERVICE, PRODUCT)")
    private String transactionType;

    private String barcode;      // Barkod okutulduysa
    private Long accessoryId;    // Barkodsuz/Manuel aksesuar seçildiyse
    private Long serviceId;      // Ekrandan hizmet tıklandıysa (Faturalı Hat, İnternet vb.)

    @NotNull
    @Positive(message = "Miktar en az 1 olmalıdır")
    private Integer quantity = 1;
}