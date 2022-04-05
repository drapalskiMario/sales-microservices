package br.com.microservices.productapi.modules.supplier.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SupplierRequestDTO {

    @NotEmpty(message = "name must not be empty")
    private String name;
}
