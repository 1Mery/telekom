package com.demo.telekom.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessorySaleRequestDto {

    private String barcode;
    private Long accessoryId;

    @Positive(message = "Miktar en az 1 olmalıdır")
    private Integer quantity = 1;
}