package br.com.microservices.productapi.modules.category.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryRequestDTO {

    @NotEmpty(message = "description must not be empty")
    private String description;
}
