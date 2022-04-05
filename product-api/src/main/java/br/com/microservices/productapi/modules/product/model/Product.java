package br.com.microservices.productapi.modules.product.model;

import br.com.microservices.productapi.modules.category.model.Category;
import br.com.microservices.productapi.modules.product.dto.ProductRequestDTO;
import br.com.microservices.productapi.modules.supplier.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantityAvailable;

    @ManyToOne
    @JoinColumn(name = "fk_supplier", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "fk_category", nullable = false)
    private Category category;

    public static Product of(ProductRequestDTO requestDTO, Supplier supplier, Category category) {
        return Product
                .builder()
                .name(requestDTO.getName())
                .quantityAvailable(requestDTO.getQuantityAvailable())
                .supplier(supplier)
                .category(category)
                .build();
    }

    public void updateStock(Integer quantity) {
        this.quantityAvailable = this.quantityAvailable - quantity;
    }
}
