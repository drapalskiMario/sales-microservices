package br.com.microservices.productapi.modules.category.controller;

import br.com.microservices.productapi.modules.category.dto.CategoryRequestDTO;
import br.com.microservices.productapi.modules.category.dto.CategoryResponseDTO;
import br.com.microservices.productapi.modules.category.model.Category;
import br.com.microservices.productapi.modules.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        categoryService.save(categoryRequestDTO);
    }

    @GetMapping
    public List<CategoryResponseDTO> findAll() {
        return this.categoryService
                .findAll()
                .stream()
                .map(CategoryResponseDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById (@PathVariable Integer id) {
        return this.categoryService
                .findById(id)
                .map(category -> CategoryResponseDTO.of(category))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/description/{desc}")
    public List<CategoryResponseDTO> findByDescription(@PathVariable String desc) {
        return this.categoryService
                .findByDescription(desc)
                .stream()
                .map(CategoryResponseDTO::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        Category category = this.categoryService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.categoryService.delete(category);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid CategoryRequestDTO requestDTO) {
        Category category = this.categoryService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.categoryService.update(category, requestDTO);
    }

}
