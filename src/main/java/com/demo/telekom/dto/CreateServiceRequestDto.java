package com.demo.telekom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateServiceRequestDto {

    @NotBlank(message = "Hizmet adı boş olamaz")
    private String name;

}