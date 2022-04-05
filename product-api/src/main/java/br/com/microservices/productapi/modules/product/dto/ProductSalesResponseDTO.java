package br.com.microservices.productapi.modules.product.dto;

import br.com.microservices.productapi.modules.category.dto.CategoryResponseDTO;
import br.com.microservices.productapi.modules.product.model.Product;
import br.com.microservices.productapi.modules.sales.dto.SalesProductResponse;
import br.com.microservices.productapi.modules.supplier.dto.SupplierResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSalesResponseDTO {

    private Integer id;
    private String name;
    private Integer quantityAvailable;
    private SupplierResponseDTO supplier;
    private CategoryResponseDTO category;
    private SalesProductResponse sales;


    public static ProductSalesResponseDTO of(Product product, SalesProductResponse sales) {
        return ProductSalesResponseDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvailable(product.getQuantityAvailable())
                .supplier(SupplierResponseDTO.of(product.getSupplier()))
                .category(CategoryResponseDTO.of(product.getCategory()))
                .sales(sales)
                .build();
    }
}
