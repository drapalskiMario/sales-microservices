package br.com.microservices.productapi.modules.category.dto;

import br.com.microservices.productapi.modules.category.model.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryResponseDTO {

    private Integer id;
    private String description;

    public static CategoryResponseDTO of(Category category) {
        CategoryResponseDTO response = new CategoryResponseDTO();
        BeanUtils.copyProperties(category, response);
        return response;
    }
}
