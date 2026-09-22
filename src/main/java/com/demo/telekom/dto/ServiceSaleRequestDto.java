package com.demo.telekom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceSaleRequestDto {

    @NotNull(message = "Hizmet seçilmelidir")
    private Long serviceId;

    @Positive(message = "Miktar en az 1 olmalıdır")
    private Integer quantity = 1;
}