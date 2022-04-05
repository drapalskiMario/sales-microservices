package br.com.microservices.productapi.modules.supplier.controller;

import br.com.microservices.productapi.modules.product.model.Product;
import br.com.microservices.productapi.modules.supplier.dto.SupplierRequestDTO;
import br.com.microservices.productapi.modules.supplier.dto.SupplierResponseDTO;
import br.com.microservices.productapi.modules.supplier.model.Supplier;
import br.com.microservices.productapi.modules.supplier.service.SupplierService;
import feign.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid SupplierRequestDTO requestDTO) {
        this.supplierService.save(requestDTO);
    }

    @GetMapping
    public List<SupplierResponseDTO> findAll() {
        return this.supplierService
                .findAll()
                .stream()
                .map(SupplierResponseDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SupplierResponseDTO findById (@PathVariable Integer id) {
        return this.supplierService
                .findById(id)
                .map(category -> SupplierResponseDTO.of(category))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponseDTO> findByDescription(@PathVariable String name) {
        return this.supplierService
                .findByName(name)
                .stream()
                .map(SupplierResponseDTO::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        Supplier supplier = this.supplierService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.supplierService.delete(supplier);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid SupplierRequestDTO requestDTO) {
        Supplier supplier = this.supplierService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.supplierService.update(supplier, requestDTO);
    }
}
