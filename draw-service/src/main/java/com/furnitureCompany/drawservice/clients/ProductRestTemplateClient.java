package com.furnitureCompany.drawservice.clients;

import com.furnitureCompany.drawservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Product> getProductsByIds(List<Long> productIds) {
        String ids = productIds.stream().map(id -> id.toString()).collect(Collectors.joining(","));
        ResponseEntity<List> restExchange = restTemplate.exchange(
                "http://localhost:5555/product_service/v1/products/productIds/{product_ids}",
                HttpMethod.GET,
                null, List.class, ids);

        return restExchange.getBody();
    }
}
