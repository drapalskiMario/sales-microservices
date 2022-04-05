package br.com.microservices.productapi.modules.category.model;

import br.com.microservices.productapi.modules.category.dto.CategoryRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    public static Category of(CategoryRequestDTO categoryRequestDTO) {
        Category response = new Category();
        BeanUtils.copyProperties(categoryRequestDTO, response);
        return response;
    }
}
