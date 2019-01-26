package com.furnitureCompany.purchaseorderservice.clients;

import com.furnitureCompany.purchaseorderservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Product getProductsById(Long productId) {
        ResponseEntity<Product> restExchange = restTemplate.exchange(
                "http://localhost:5555/product_service/v1/products/{id}",
                HttpMethod.GET,
                null, Product.class, productId);

        return restExchange.getBody();
    }
}
