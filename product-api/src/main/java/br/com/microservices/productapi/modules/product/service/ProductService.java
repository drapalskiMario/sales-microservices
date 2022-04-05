package br.com.microservices.productapi.modules.product.service;

import br.com.microservices.productapi.modules.category.model.Category;
import br.com.microservices.productapi.modules.category.service.CategoryService;
import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import br.com.microservices.productapi.modules.product.dto.*;
import br.com.microservices.productapi.modules.product.model.Product;
import br.com.microservices.productapi.modules.product.repository.ProductRepository;
import br.com.microservices.productapi.modules.sales.client.SalesClient;
import br.com.microservices.productapi.modules.sales.dto.SalesConfirmationDTO;
import br.com.microservices.productapi.modules.sales.dto.SalesProductResponse;
import br.com.microservices.productapi.modules.sales.enums.SalesStatus;
import br.com.microservices.productapi.modules.sales.sender.SalesConfirmationSender;
import br.com.microservices.productapi.modules.supplier.model.Supplier;
import br.com.microservices.productapi.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.bouncycastle.util.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierService supplierService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SalesConfirmationSender salesConfirmationSender;

    @Autowired
    SalesClient salesClient;

    public void save(ProductRequestDTO requestDTO) {
        Supplier supplier = this.supplierService
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new DomainApplicationException("invalid supplier id"));
        Category category = this.categoryService
                .findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new DomainApplicationException("invalid category id"));
        this.productRepository.save(Product.of(requestDTO, supplier, category));
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return this.productRepository.findById(id);
    }

    public List<Product> findByName(String description) {
        if (description.isEmpty()) {
            throw new DomainApplicationException("description must be not empty");
        }
        return productRepository.findByNameIgnoreCaseContaining(description);
    }

    public List<Product> findByCategory(Integer id) {
        return productRepository.findByCategoryId(id);
    }

    public List<Product> findBySupplier(Integer id) {
        return productRepository.findBySupplierId(id);
    }

    public void delete(Product product) {
        this.productRepository.delete(product);
    }

    public void update(Product product, ProductRequestDTO requestDTO) {
        Supplier supplier = this.supplierService
                .findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new DomainApplicationException("invalid supplier id"));
        Category category = this.categoryService
                .findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new DomainApplicationException("invalid category id"));
        Product productOf = Product.of(requestDTO, supplier, category);
        productOf.setId(product.getId());
        this.productRepository.save(productOf);
    }

    public Boolean existsByCategoryId(Integer id) {
        return this.productRepository.existsByCategoryId(id);
    }

    public Boolean existsBySupplierId(Integer id) {
        return this.productRepository.existsBySupplierId(id);
    }

    public void updateProductStock(ProductStockDTO productStockDTO) {
        try {
            this.updateStock(productStockDTO);
            SalesConfirmationDTO approvedMessage = new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.APPROVED);
            this.salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        } catch (Exception exception) {
            SalesConfirmationDTO rejectMessage = new SalesConfirmationDTO(productStockDTO.getSalesId(), SalesStatus.REJECTED);
            log.error("Error while trying to update stock for with erro: {}", exception.getMessage(), exception);
            this.salesConfirmationSender.sendSalesConfirmationMessage(rejectMessage);
        }
    }

    public void updateStock(ProductStockDTO productStockDTO) {
        this.validateStockData(productStockDTO);
        List<Product> validatedProducts = new ArrayList<>();
        productStockDTO
                .getProducts()
                .forEach(salesProduct -> {
                    Optional<Product> productSaved= this.findById(salesProduct.getProductId());
                    if (salesProduct.getQuantity() > productSaved.get().getQuantityAvailable()) {
                        throw new DomainApplicationException(
                                String.format("the product %s oi out of stock", productSaved.get().getId()));
                    }
                    productSaved.get().updateStock(salesProduct.getQuantity());
                    validatedProducts.add(productSaved.get());
                });
        if (!validatedProducts.isEmpty()) {
            this.productRepository.saveAll(validatedProducts);
        }
    }

    private void validateStockData(ProductStockDTO productDto) {
        if (Objects.isNull(productDto)
                || productDto.getSalesId().isEmpty() || productDto.getProducts().isEmpty() ) {
            throw new DomainApplicationException("productDTO, salesId, products must be not empty");
        } else {
            productDto
                    .getProducts()
                    .forEach(salesProduct -> {
                        if (salesProduct.getProductId() == null || salesProduct.getQuantity() == null) {
                            throw new DomainApplicationException("productId, quantity must be not empty");
                        }
                    });
        }
    }

    public void checkProductsStock(ProductCheckStockRequestDTO requestDTO) {
        requestDTO
                .getProducts()
                .forEach(this::validateStock);
    }

    public void validateStock(ProductQuantityDTO productQuantityDTO) {
        if (Objects.isNull(productQuantityDTO.getProductId()) || Objects.isNull(productQuantityDTO.getQuantity())) {
            throw new DomainApplicationException("The request data must be informed.");
        }
        Optional<Product> product = this.findById(productQuantityDTO.getProductId());
        if (product.get().getQuantityAvailable() < productQuantityDTO.getQuantity()) {
            throw new DomainApplicationException(String.format("The product %s is out of stock", product.get().getId()));
        }
    }

    public ProductSalesResponseDTO findProductSales(Integer id) {
        Optional<Product> product = findById(id);
        try {
            SalesProductResponse sales = this.salesClient
                    .findSalesByProductId(product.get().getId())
                    .orElseThrow(() -> new DomainApplicationException("The sales was not found by this product"));
            return ProductSalesResponseDTO.of(product.get(), sales);
        } catch (Exception exception) {
            throw new DomainApplicationException("There was an error trying to get the product's sales.");
        }
    }
}
