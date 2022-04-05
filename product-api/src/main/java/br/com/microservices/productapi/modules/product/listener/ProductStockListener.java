package br.com.microservices.productapi.modules.product.listener;

import br.com.microservices.productapi.modules.product.dto.ProductStockDTO;
import br.com.microservices.productapi.modules.product.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO productStockDTO) {
        this.productService.updateProductStock(productStockDTO);
    }
}
