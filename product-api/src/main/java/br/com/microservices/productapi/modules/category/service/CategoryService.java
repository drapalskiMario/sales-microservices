package br.com.microservices.productapi.modules.category.service;

import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import br.com.microservices.productapi.modules.category.dto.CategoryRequestDTO;
import br.com.microservices.productapi.modules.category.model.Category;
import br.com.microservices.productapi.modules.category.repository.CategoryRepository;
import br.com.microservices.productapi.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productService;

    public void save(CategoryRequestDTO request) {
        this.categoryRepository.save(Category.of(request));
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> findById(Integer id) {
        return this.categoryRepository.findById(id);
    }

    public List<Category> findByDescription(String description) {
        if (description.isEmpty()) {
            throw new DomainApplicationException("description must be not empty");
        }
        return categoryRepository.findByDescriptionIgnoreCaseContaining(description);
    }

    public void delete(Category category) {
        Boolean alreadyDefined = this.productService.existsBySupplierId(category.getId());
        if (alreadyDefined) {
            throw new DomainApplicationException(
                    "You cannot delete this category because it's already defined by a product.");
        }
        this.categoryRepository.delete(category);
    }

    public void update(Category category, CategoryRequestDTO requestDTO) {
        Category categoryOf = Category.of(requestDTO);
        categoryOf.setId(category.getId());
        this.categoryRepository.save(categoryOf);
    }
}
