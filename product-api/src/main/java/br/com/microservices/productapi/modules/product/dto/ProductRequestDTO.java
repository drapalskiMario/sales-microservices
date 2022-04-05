package br.com.microservices.productapi.modules.product.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequestDTO {

    @NotEmpty(message = "name not must be empty")
    private String name;

    @NotNull(message = "quantityAvailable not must be empty")
    @Min(value = 0, message = "invalid quantity number")
    private Integer quantityAvailable;

    @NotNull(message = "supplierId not must be empty")
    @Min(value = 0, message = "invalid supplierId number")
    private Integer supplierId;

    @NotNull(message = "categoryId not must be empty")
    @Min(value = 0, message = "invalid categoryId number")
    private Integer categoryId;
}
