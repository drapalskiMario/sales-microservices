package br.com.microservices.productapi.modules.supplier.model;

import br.com.microservices.productapi.modules.category.dto.CategoryRequestDTO;
import br.com.microservices.productapi.modules.category.model.Category;
import br.com.microservices.productapi.modules.supplier.dto.SupplierRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public static Supplier of(SupplierRequestDTO requestDTO) {
        Supplier response = new Supplier();
        BeanUtils.copyProperties(requestDTO, response);
        return response;
    }
}
