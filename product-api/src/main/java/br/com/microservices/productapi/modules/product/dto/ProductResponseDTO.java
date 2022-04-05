package br.com.microservices.productapi.modules.product.dto;

import br.com.microservices.productapi.modules.category.dto.CategoryResponseDTO;
import br.com.microservices.productapi.modules.product.model.Product;
import br.com.microservices.productapi.modules.supplier.dto.SupplierResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {

    private Integer id;
    private String name;
    private Integer quantityAvailable;
    private SupplierResponseDTO supplier;
    private CategoryResponseDTO category;


    public static ProductResponseDTO of(Product product) {
        return ProductResponseDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvailable(product.getQuantityAvailable())
                .supplier(SupplierResponseDTO.of(product.getSupplier()))
                .category(CategoryResponseDTO.of(product.getCategory()))
                .build();
    }
}
