package br.com.microservices.productapi.modules.supplier.dto;

import br.com.microservices.productapi.modules.supplier.model.Supplier;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class SupplierResponseDTO {

    private Integer id;
    private String name;

    public static SupplierResponseDTO of(Supplier supplier) {
        SupplierResponseDTO responseDTO = new SupplierResponseDTO();
        BeanUtils.copyProperties(supplier, responseDTO);
        return responseDTO;
    }
}
