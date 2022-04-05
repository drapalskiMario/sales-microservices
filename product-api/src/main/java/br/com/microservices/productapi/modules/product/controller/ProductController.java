package br.com.microservices.productapi.modules.product.controller;

import br.com.microservices.productapi.modules.product.dto.ProductCheckStockRequestDTO;
import br.com.microservices.productapi.modules.product.dto.ProductRequestDTO;
import br.com.microservices.productapi.modules.product.dto.ProductResponseDTO;
import br.com.microservices.productapi.modules.product.dto.ProductSalesResponseDTO;
import br.com.microservices.productapi.modules.product.model.Product;
import br.com.microservices.productapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid ProductRequestDTO requestDTO) {
        this.productService.save(requestDTO);
    }

    @GetMapping
    public List<ProductResponseDTO> findAll() {
        return this.productService
                .findAll()
                .stream()
                .map(ProductResponseDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById (@PathVariable Integer id) {
        return this.productService
                .findById(id)
                .map(ProductResponseDTO::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public List<ProductResponseDTO> findByDescription(@PathVariable String name) {
        return this.productService
                .findByName(name)
                .stream()
                .map(ProductResponseDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{id}")
    public List<ProductResponseDTO> findByCategoryId (@PathVariable Integer id) {
        return this.productService
                .findByCategory(id)
                .stream()
                .map(ProductResponseDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/supplier/{id}")
    public List<ProductResponseDTO> findBySupplierId (@PathVariable Integer id) {
        return this.productService
                .findBySupplier(id)
                .stream()
                .map(ProductResponseDTO::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        Product product = this.productService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.productService.delete(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid ProductRequestDTO requestDTO) {
        Product product = this.productService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.productService.update(product, requestDTO);
    }

    @PostMapping("/check-stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkProductStock(@RequestBody ProductCheckStockRequestDTO requestDTO) {
        this.productService.checkProductsStock(requestDTO);
    }

    @GetMapping("/{productId}/sales")
    public ProductSalesResponseDTO findProductSales(@PathVariable Integer productId) {
        return this.productService.findProductSales(productId);
    }
}
