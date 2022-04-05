package br.com.microservices.productapi.modules.supplier.service;

import br.com.microservices.productapi.modules.exception.DomainApplicationException;
import br.com.microservices.productapi.modules.product.service.ProductService;
import br.com.microservices.productapi.modules.supplier.dto.SupplierRequestDTO;
import br.com.microservices.productapi.modules.supplier.model.Supplier;
import br.com.microservices.productapi.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductService productService;

    public void save(SupplierRequestDTO requestDTO) {
        this.supplierRepository.save(Supplier.of(requestDTO));
    }

    public List<Supplier> findAll() {
        return this.supplierRepository.findAll();
    }

    public Optional<Supplier> findById(Integer id) {
        return this.supplierRepository.findById(id);
    }

    public List<Supplier> findByName(String name) {
        if (name.isEmpty()) {
            throw new DomainApplicationException("name must be not empty");
        }
        return supplierRepository.findByNameIgnoreCaseContaining(name);
    }

    public void delete(Supplier supplier) {
        Boolean alreadyDefined = this.productService.existsBySupplierId(supplier.getId());
        if (alreadyDefined) {
            throw new DomainApplicationException(
                    "You cannot delete this supplier because it's already defined by a product.");
        }
        this.supplierRepository.delete(supplier);
    }

    public void update(Supplier supplier, SupplierRequestDTO requestDTO) {
        Supplier supplierOf = Supplier.of(requestDTO);
        supplierOf.setId(supplier.getId());
        this.supplierRepository.save(supplierOf);
    }
}
